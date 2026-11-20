/* ==========================================================
   api.js: única camada do front que conversa com o back-end
   O app.js nunca usa fetch direto, só as funções deste arquivo.
   ========================================================== */

/* ---------- CONFIGURAÇÃO ---------- */

/* Endereço base da API Spring (ajuste se mudar a porta ou o caminho) */
const API_URL = "http://localhost:8080/sorveteria";

/* true  = usa os dados simulados abaixo (back-end ainda não existe)
   false = chama a API Spring de verdade */
const USAR_DADOS_SIMULADOS = false;

/* ---------- DADOS SIMULADOS ----------
   Têm o MESMO formato que o back-end deve devolver.
   Quando o banco estiver pronto, estes dados deixam de ser usados. */
const DADOS_SIMULADOS = {
    nomeBase: "Casquinha",
    precoBase: 5.0,
    sabores: [
        { id: "chocolate", nome: "Chocolate", emoji: "🍫", preco: 2.0 },
        { id: "morango",   nome: "Morango",   emoji: "🍓", preco: 2.0 },
        { id: "baunilha",  nome: "Baunilha",  emoji: "🍨", preco: 2.0 },
    ],
};

/* ---------- FUNÇÃO BASE DE REQUISIÇÃO ----------
   Centraliza o fetch: define JSON, trata erros e evita repetição de código */
async function requisicao(caminho, opcoes = {}) {
    const resposta = await fetch(`${API_URL}${caminho}`, {
        headers: { "Content-Type": "application/json" },
        ...opcoes,
    });

    /* Respostas 4xx/5xx viram erro, para o app.js poder tratar com try/catch */
    if (!resposta.ok) {
        throw new Error(`Erro ${resposta.status} ao acessar ${caminho}`);
    }
    return resposta.json();
}

/* ==========================================================
   FUNÇÕES USADAS PELO app.js
   ========================================================== */

/* Busca o preço base e a lista de sabores (nome e preço vindos do banco).

   Resposta:  { "precoBase": 5.0,
                "sabores": [ { "id": "chocolate", "nome": "Chocolate", "preco": 2.0 } ] } */
async function buscarSabores() {
    if (USAR_DADOS_SIMULADOS) {
        return DADOS_SIMULADOS;
    }
    return requisicao("/sabores");
}

/* Calcula o total de um sundae com os sabores escolhidos.

   Envia:     { "sabores": ["chocolate", "morango"] }
   Resposta:  { "sabores": ["chocolate", "morango"], "total": 9.0 }
   (é aqui que o Decorator vai somar os preços no Spring) */
async function calcularPedido(sabores) {
    if (USAR_DADOS_SIMULADOS) {
        const precos = Object.fromEntries(
            DADOS_SIMULADOS.sabores.map((s) => [s.id, s.preco])
        );
        const total = sabores.reduce(
            (soma, id) => soma + (precos[id] ?? 0),
            DADOS_SIMULADOS.precoBase
        );
        return { sabores, total };
    }

    return requisicao("/sundae/calcular", {
        method: "POST",
        body: JSON.stringify({ sabores }),
    });
}

/* Envia o pedido final para o back-end salvar no banco.

   Envia:     { "sabores": ["chocolate"] }
   Resposta:  { "id": 12, "sabores": ["chocolate"], "total": 7.0 } */
async function finalizarPedido(sabores) {
    if (USAR_DADOS_SIMULADOS) {
        const pedido = await calcularPedido(sabores);
        return { id: null, ...pedido };
    }

    return requisicao("/pedidos", {
        method: "POST",
        body: JSON.stringify({ sabores }),
    });
}