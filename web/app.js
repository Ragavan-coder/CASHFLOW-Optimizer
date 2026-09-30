document.addEventListener('DOMContentLoaded', () => {
    fetchBalances();
    fetchDebts();
    fetchSettlement();
});

async function fetchBalances() {
    try {
        const response = await fetch('/api/balances');
        const data = await response.json();
        const container = document.getElementById('balancesList');
        container.innerHTML = '';
        
        for (const [person, balance] of Object.entries(data)) {
            const div = document.createElement('div');
            div.className = 'item';
            const valClass = balance > 0 ? 'positive' : 'negative';
            const label = balance > 0 ? 'RECEIVES' : 'OWES';
            div.innerHTML = `
                <span>${person}</span>
                <span class="${valClass}">${label} ₹${Math.abs(balance).toLocaleString()}</span>
            `;
            container.appendChild(div);
        }
    } catch (e) {
        console.error('Failed to load balances');
    }
}

async function fetchDebts() {
    try {
        const response = await fetch('/api/debts');
        const debts = await response.json();
        const container = document.getElementById('debtsList');
        container.innerHTML = '';
        
        document.getElementById('originalTx').innerText = debts.length;
        
        debts.forEach(d => {
            const div = document.createElement('div');
            div.className = 'item';
            div.innerHTML = `
                <span>${d.debtor} &rarr; ${d.creditor}</span>
                <span>₹${d.amount.toLocaleString()}</span>
            `;
            container.appendChild(div);
        });
    } catch (e) {
        console.error('Failed to load debts');
    }
}

async function fetchSettlement() {
    try {
        const response = await fetch('/api/settlement');
        const txs = await response.json();
        const container = document.getElementById('settlementList');
        container.innerHTML = '';
        
        document.getElementById('optimizedTx').innerText = txs.length;
        
        txs.forEach((t, i) => {
            const div = document.createElement('div');
            div.className = 'item';
            div.innerHTML = `
                <span>${i + 1}. ${t.from} &rarr; ${t.to}</span>
                <span class="positive">₹${t.amount.toLocaleString()}</span>
            `;
            container.appendChild(div);
        });
    } catch (e) {
        console.error('Failed to load settlement');
    }
}
