/* ==========================================================
   app.js: controla a interface
   Todos os nomes e preços vêm da API (via api.js); nada fixo aqui.
   ========================================================== */

/* ---------- ESTADO ---------- */
let catalogo = null;                       /* { nomeBase, precoBase, sabores[] } vindo da API */
const saboresSelecionados = new Set();     /* ids dos sabores escolhidos, sem repetir */

/* ---------- ELEMENTOS DA TELA ---------- */
const subtitulo    = document.getElementById("subtitulo");
const mensagemErro = document.getElementById("mensagem-erro");
const areaSabores  = document.getElementById("sabores");
const listaPedido  = document.getElementById("lista-pedido");
const totalPedido  = document.getElementById("total");
const btnLimpar    = document.getElementById("btn-limpar");
const btnFinalizar = document.getElementById("btn-finalizar");

/* ---------- FUNÇÕES AUXILIARES ---------- */

/* Formata número como moeda brasileira. Ex.: 7 -> "R$ 7,00" */
function formatarMoeda(valor) {
    return valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

/* Mostra uma mensagem de erro amigável na tela */
function mostrarErro(texto) {
    mensagemErro.textContent = texto;
    mensagemErro.hidden = false;
}

/* Esconde a mensagem de erro */
function limparErro() {
    mensagemErro.hidden = true;
}

/* Habilita ou desabilita os botões de ação */
function habilitarAcoes(habilitar) {
    btnLimpar.disabled = !habilitar;
    btnFinalizar.disabled = !habilitar;
}
/* Notificação flutuante (toast) que some sozinha.
   tipo: "sucesso" ou "erro". Os estilos ficam no style.css */
function mostrarToast({ titulo, linhas = [], total = null, tipo = "sucesso" }) {
    let area = document.getElementById("toasts");
    if (!area) {
        area = document.createElement("div");
        area.id = "toasts";
        document.body.appendChild(area);
    }

    const toast = document.createElement("div");
    toast.className = `toast ${tipo}`;
    toast.setAttribute("role", "status");

    const tituloEl = document.createElement("div");
    tituloEl.className = "toast-titulo";
    tituloEl.textContent = titulo;
    toast.appendChild(tituloEl);

    linhas.forEach((texto) => {
        const linha = document.createElement("div");
        linha.className = "toast-linha";
        linha.textContent = texto;
        toast.appendChild(linha);
    });

    if (total) {
        const totalEl = document.createElement("div");
        totalEl.className = "toast-total";
        totalEl.textContent = `Total: ${total}`;
        toast.appendChild(totalEl);
    }

    area.appendChild(toast);

    /* Some depois de 4,5 segundos (ou ao clicar nele) */
    const remover = () => {
        toast.classList.add("saindo");
        toast.addEventListener("animationend", () => toast.remove(), { once: true });
    };
    setTimeout(remover, 4500);
    toast.addEventListener("click", remover);
}
/* ---------- CONSTRUÇÃO DA TELA ---------- */

/* Mostra o nome e o preço base no subtítulo */
function desenharSubtitulo() {
    subtitulo.textContent = `${catalogo.nomeBase} base: ${formatarMoeda(catalogo.precoBase)}`;
}

/* Cria um botão para cada sabor que veio da API */
function desenharSabores() {
    areaSabores.innerHTML = "";

    catalogo.sabores.forEach((sabor) => {
        const botao = document.createElement("button");
        botao.className = "sabor";
        botao.dataset.sabor = sabor.id;                 /* identificador usado nos cliques */

        /* Texto do botão: emoji (opcional) + nome */
        botao.append(`${sabor.emoji ? sabor.emoji + " " : ""}${sabor.nome}`);

        /* Preço do sabor em uma linha menor abaixo do nome */
        const preco = document.createElement("span");
        preco.className = "preco-sabor";
        preco.textContent = `+ ${formatarMoeda(sabor.preco)}`;
        botao.appendChild(preco);

        areaSabores.appendChild(botao);
    });
}

/* Atualiza a lista do pedido e o total (o cálculo é feito pelo back-end) */
async function atualizarPedido() {
    try {
        const pedido = await calcularPedido([...saboresSelecionados]);

        listaPedido.innerHTML = "";

        /* Primeiro item: a base (nome vindo da API) */
        const itemBase = document.createElement("li");
        itemBase.textContent = catalogo.nomeBase;
        listaPedido.appendChild(itemBase);

        /* Um item para cada sabor escolhido, com o nome cadastrado */
        pedido.sabores.forEach((id) => {
            const sabor = catalogo.sabores.find((s) => s.id === id);
            const item = document.createElement("li");
            item.textContent = sabor ? sabor.nome : id;
            listaPedido.appendChild(item);
        });

        totalPedido.textContent = formatarMoeda(pedido.total);
        limparErro();
    } catch (erro) {
        mostrarErro("Não foi possível calcular o total. Tente novamente.");
    }
}

/* ---------- EVENTOS ---------- */

/* Clique em um sabor.
   Como os botões são criados depois, escutamos o clique na área toda (delegação de eventos) */
areaSabores.addEventListener("click", (evento) => {
    const botao = evento.target.closest(".sabor");
    if (!botao) return;                               /* clique fora de um botão de sabor */

    const id = botao.dataset.sabor;

    if (saboresSelecionados.has(id)) {
        saboresSelecionados.delete(id);
    } else {
        saboresSelecionados.add(id);
    }

    botao.classList.toggle("ativo");                  /* liga/desliga o visual rosa do CSS */
    atualizarPedido();
});

/* Botão Limpar: zera a seleção */
btnLimpar.addEventListener("click", () => {
    saboresSelecionados.clear();
    areaSabores.querySelectorAll(".sabor").forEach((b) => b.classList.remove("ativo"));
    atualizarPedido();
});

/* Botão Finalizar: envia o pedido ao back-end */
btnFinalizar.addEventListener("click", async() => {
habilitarAcoes(false);
try{
     const pedido = await finalizarPedido([...saboresSelecionados]);

        /* Nomes cadastrados dos sabores escolhidos */
        const nomes = pedido.sabores.map((id) => {
        const sabor =  catalogo.sabores.find((s) => s.id === id);
        return sabor ? sabor.nome : id;
        });
        mostrarToast({
         titulo: pedido.id ? `🍦 Pedido nº ${pedido.id} finalizado!` : "🍦 Pedido finalizado!",
         linhas: [nomes.length ? `${catalogo.nomeBase} + ${nomes.join(", ")}` : catalogo.nomeBase],
         total: formatarMoeda(pedido.total),
         });
         saboresSelecionados.clear();
         areaSabores.querySelectorAll(".sabor").forEach((b) => b.classList.remove("ativo"));
         await atualizarPedido();
         } catch (erro) {
         mostrarErro("Não foi possível finalizar o pedido. Tente novamente.");
         mostrarToast({
          titulo: "Ops! Algo deu errado",
          linhas: ["Não foi possível finalizar o pedido."],
          tipo: "erro",
         });
       } finally {
       habilitarAcoes(true);
       }
});


/* ---------- INICIALIZAÇÃO ----------
   Ao abrir a página: busca os dados na API e monta a tela */
async function iniciar() {
    try {
        catalogo = await buscarSabores();
        desenharSubtitulo();
        desenharSabores();
        await atualizarPedido();
        habilitarAcoes(true);
    } catch (erro) {
        subtitulo.textContent = "Indisponível";
        areaSabores.innerHTML = "";
        mostrarErro("Não foi possível carregar os sabores. Verifique se o servidor está ligado.");
    }
}

iniciar();