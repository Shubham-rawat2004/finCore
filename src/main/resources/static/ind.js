const API_BASE = "http://localhost:1000";

let authToken = null;
let currentUser = null;

// ----- Helpers -----
function setAuth(token, username, role) {
  authToken = token;
  currentUser = { username, role };
  document.getElementById("currentUser").textContent = username;
  document.getElementById("currentRole").textContent = "ROLE: " + role;
  showToast("Logged in as " + username + " (" + role + ")");
}

function clearAuth() {
  authToken = null;
  currentUser = null;
  document.getElementById("currentUser").textContent = "Not logged in";
  document.getElementById("currentRole").textContent = "ROLE: GUEST";
  showToast("Logged out");
}

function getHeaders(isJson = true) {
  const h = {};
  if (isJson) h["Content-Type"] = "application/json";
  if (authToken) h["Authorization"] = "Bearer " + authToken;
  return h;
}

function showToast(msg) {
  const toast = document.getElementById("toast");
  toast.textContent = msg;
  toast.classList.remove("hidden");
  toast.classList.add("show");
  setTimeout(() => {
    toast.classList.remove("show");
    setTimeout(() => toast.classList.add("hidden"), 200);
  }, 2500);
}

// ----- View switching -----
document.querySelectorAll(".nav-link").forEach(btn => {
  btn.addEventListener("click", () => {
    document.querySelectorAll(".nav-link").forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
    const view = btn.dataset.view;
    document.querySelectorAll(".view").forEach(v => v.classList.remove("active"));
    document.getElementById(view + "View").classList.add("active");
  });
});

// ----- Auth Modal -----
const authModal = document.getElementById("authModal");
document.getElementById("openAuthModal").onclick = () =>
  authModal.classList.remove("hidden");
document.getElementById("closeAuthModal").onclick = () =>
  authModal.classList.add("hidden");

document.querySelectorAll(".tab-link").forEach(tabBtn => {
  tabBtn.addEventListener("click", () => {
    document.querySelectorAll(".tab-link").forEach(b => b.classList.remove("active"));
    document.querySelectorAll(".tab").forEach(t => t.classList.remove("active"));
    tabBtn.classList.add("active");
    document.getElementById(tabBtn.dataset.tab).classList.add("active");
  });
});

// ----- Auth forms -----
document.getElementById("loginForm").addEventListener("submit", async e => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(e.target).entries());
  try {
    const res = await fetch(API_BASE + "/api/auth/login", {
      method: "POST",
      headers: getHeaders(),
      body: JSON.stringify(data)
    });
    if (!res.ok) throw await res.json();
    const body = await res.json();
    setAuth(body.accessToken || body.token, body.username, body.role);
    authModal.classList.add("hidden");
  } catch (err) {
    showToast(err.message || "Login failed");
  }
});

document.getElementById("registerForm").addEventListener("submit", async e => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(e.target).entries());
  if (!data.customerId) data.customerId = null;
  try {
    const res = await fetch(API_BASE + "/api/auth/register", {
      method: "POST",
      headers: getHeaders(),
      body: JSON.stringify(data)
    });
    if (!res.ok) throw await res.json();
    const body = await res.json();
    setAuth(body.accessToken, body.username, body.role);
    authModal.classList.add("hidden");
  } catch (err) {
    showToast(err.message || "Registration failed");
  }
});

document.getElementById("logoutBtn").onclick = clearAuth;

// ----- Customer View -----
document.getElementById("customerForm").addEventListener("submit", async e => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(e.target).entries());
  try {
    const res = await fetch(API_BASE + "/api/customers", {
      method: "POST",
      headers: getHeaders(),
      body: JSON.stringify(data)
    });
    const body = await res.json();
    if (!res.ok) throw body;
    showToast("Customer created (ID " + body.id + ")");
    e.target.reset();
  } catch (err) {
    showToast(err.message || "Failed to create customer");
  }
});

document.getElementById("loadCustomersBtn").addEventListener("click", async () => {
  try {
    const res = await fetch(API_BASE + "/api/customers", {
      headers: getHeaders(false)
    });
    const data = await res.json();
    const tbody = document.querySelector("#customersTable tbody");
    tbody.innerHTML = "";
    data.forEach(c => {
      const tr = document.createElement("tr");
      tr.innerHTML = `<td>${c.id}</td><td>${c.name}</td><td>${c.email}</td><td>${c.city || ""}</td>`;
      tbody.appendChild(tr);
    });
    document.getElementById("totalCustomers").textContent = data.length;
    showToast("Loaded " + data.length + " customers");
  } catch (err) {
    showToast("Failed to load customers");
  }
});

// ----- Account View -----
document.getElementById("accountForm").addEventListener("submit", async e => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(e.target).entries());
  data.customerId = Number(data.customerId);
  data.initialDeposit = Number(data.initialDeposit);
  try {
    const res = await fetch(API_BASE + "/api/accounts", {
      method: "POST",
      headers: getHeaders(),
      body: JSON.stringify(data)
    });
    const body = await res.json();
    if (!res.ok) throw body;
    showToast("Account created (ID " + body.id + ")");
    e.target.reset();
  } catch (err) {
    showToast(err.message || "Failed to create account");
  }
});

document.getElementById("fetchCustomerAccountsBtn").addEventListener("click", async () => {
  const cid = document.getElementById("accountsCustomerId").value;
  if (!cid) return;
  try {
    const res = await fetch(API_BASE + `/api/accounts/by-customer/${cid}`, {
      headers: getHeaders(false)
    });
    const data = await res.json();
    const tbody = document.querySelector("#accountsTable tbody");
    tbody.innerHTML = "";
    data.forEach(a => {
      const tr = document.createElement("tr");
      tr.innerHTML = `<td>${a.id}</td><td>${a.accountNumber}</td><td>${a.accountType}</td><td>${a.balance}</td>`;
      tbody.appendChild(tr);
    });
    document.getElementById("totalAccounts").textContent = data.length;
    showToast("Loaded " + data.length + " accounts");
  } catch (err) {
    showToast("Failed to load accounts");
  }
});

// Deposit / Withdraw / Transfer
document.getElementById("depositForm").addEventListener("submit", async e => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(e.target).entries());
  try {
    const res = await fetch(
      `${API_BASE}/api/accounts/${data.accountId}/deposit?amount=${data.amount}`,
      { method: "POST", headers: getHeaders(false) }
    );
    if (!res.ok) throw await res.json();
    showToast("Deposit successful");
  } catch (err) {
    showToast(err.message || "Deposit failed");
  }
});

document.getElementById("withdrawForm").addEventListener("submit", async e => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(e.target).entries());
  try {
    const res = await fetch(
      `${API_BASE}/api/accounts/${data.accountId}/withdraw?amount=${data.amount}`,
      { method: "POST", headers: getHeaders(false) }
    );
    if (!res.ok) throw await res.json();
    showToast("Withdraw successful");
  } catch (err) {
    showToast(err.message || "Withdraw failed");
  }
});

document.getElementById("transferForm").addEventListener("submit", async e => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(e.target).entries());
  const params =
    `fromAccountId=${data.fromAccountId}&toAccountId=${data.toAccountId}&amount=${data.amount}`;
  try {
    const res = await fetch(`${API_BASE}/api/accounts/transfer?${params}`, {
      method: "POST",
      headers: getHeaders(false)
    });
    if (!res.ok) throw await res.json();
    showToast("Transfer successful");
  } catch (err) {
    showToast(err.message || "Transfer failed");
  }
});

// ----- Transactions View -----
document.getElementById("txnForm").addEventListener("submit", async e => {
  e.preventDefault();
  const raw = Object.fromEntries(new FormData(e.target).entries());
  const data = {
    txnType: raw.txnType,
    fromAccountId: raw.fromAccountId ? Number(raw.fromAccountId) : null,
    toAccountId: raw.toAccountId ? Number(raw.toAccountId) : null,
    amount: Number(raw.amount),
    description: raw.description
  };
  try {
    const res = await fetch(API_BASE + "/api/transactions", {
      method: "POST",
      headers: getHeaders(),
      body: JSON.stringify(data)
    });
    const body = await res.json();
    if (!res.ok) throw body;
    showToast("Transaction created (ID " + body.id + ")");
  } catch (err) {
    showToast(err.message || "Transaction failed");
  }
});

document.getElementById("fetchTxByAccountBtn").addEventListener("click", async () => {
  const aid = document.getElementById("txAccountId").value;
  if (!aid) return;
  try {
    const res = await fetch(API_BASE + `/api/transactions/by-account/${aid}`, {
      headers: getHeaders(false)
    });
    const data = await res.json();
    const tbody = document.querySelector("#txTable tbody");
    tbody.innerHTML = "";
    data.forEach(t => {
      const tr = document.createElement("tr");
      tr.innerHTML = `<td>${t.id}</td><td>${t.txnType}</td><td>${t.amount}</td><td>${t.status}</td><td>${t.txnDate}</td>`;
      tbody.appendChild(tr);
    });
    document.getElementById("totalTxns").textContent = data.length;
    showToast("Loaded " + data.length + " transactions");
  } catch (err) {
    showToast("Failed to load transactions");
  }
});

// ----- My Accounts (Customer endpoints) -----
document.getElementById("loadMyAccountsBtn").addEventListener("click", async () => {
  try {
    const res = await fetch(API_BASE + "/api/customers/my-accounts", {
      headers: getHeaders(false)
    });
    const data = await res.json();
    const tbody = document.querySelector("#myAccountsTable tbody");
    tbody.innerHTML = "";
    data.forEach(a => {
      const tr = document.createElement("tr");
      tr.innerHTML = `<td>${a.accountId}</td><td>${a.accountNumber}</td><td>${a.accountType}</td><td>${a.balance}</td>`;
      tbody.appendChild(tr);
    });
    showToast("Loaded my accounts");
  } catch (err) {
    showToast("Failed to load my accounts (are you logged in as CUSTOMER?)");
  }
});

document.getElementById("loadMyTxBtn").addEventListener("click", async () => {
  const aid = document.getElementById("myAccIdForTx").value;
  if (!aid) return;
  try {
    const res = await fetch(
      API_BASE + `/api/customers/my-account/${aid}/transactions`,
      { headers: getHeaders(false) }
    );
    const data = await res.json();
    const tbody = document.querySelector("#myTxTable tbody");
    tbody.innerHTML = "";
    data.forEach(t => {
      const tr = document.createElement("tr");
      tr.innerHTML = `<td>${t.txnId}</td><td>${t.txnType}</td><td>${t.amount}</td><td>${t.status}</td><td>${t.txnDate}</td>`;
      tbody.appendChild(tr);
    });
    showToast("Loaded my transactions");
  } catch (err) {
    showToast("Failed to load my transactions");
  }
});
