const API_URL = "/api/ksiazki";

document.addEventListener("DOMContentLoaded", () => {
    ladujKsiazki();
});

function pobierzKsiazke() {
	const ocena = document.getElementById("ocena").value;
	
    return {
        tytul: document.getElementById("tytul").value.trim(),
        autor: document.getElementById("autor").value.trim(),
        opis: document.getElementById("opis").value.trim(),
        ocena: ocena ? parseInt(ocena) : null
    };
}

function wyczyscFormatke() {
    document.getElementById("idKsiazki").value = "";
    document.getElementById("tytul").value = "";
    document.getElementById("autor").value = "";
    document.getElementById("opis").value = "";
    document.getElementById("ocena").value = "";
}

async function zapiszKsiazke() {
    const idKsiazki = document.getElementById("idKsiazki").value;
    const ksiazka = pobierzKsiazke();

    if (!ksiazka.tytul || !ksiazka.autor) {
        alert("Tytuł i autor są wymagane");
        return;
    }

    const metoda = idKsiazki ? "PUT" : "POST";
    const link = idKsiazki ? `${API_URL}/${idKsiazki}` : API_URL;

    const odp = await fetch(link, {
        method: metoda,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(ksiazka)
    });

    if (!odp.ok) {
        const blad = await odp.text();
        alert(blad);
        return;
    } else {
		alert("Książka została zapisana");
	}

    wyczyscFormatke();
    ladujKsiazki();
}

async function ladujKsiazki() {
    const odp = await fetch(API_URL);
    const ksiazki = await odp.json();
    wyswietlKsiazki(ksiazki);
}

async function edytujKsiazke(idKsiazki) {
    const odp = await fetch(`${API_URL}/${idKsiazki}`);
    if (!odp.ok) {
        alert("Nie udało się pobrać książki");
        return;
    }
    const ksiazka = await odp.json();
    wypelnijDane(ksiazka);
}

function wypelnijDane(ksiazka) {
    document.getElementById("idKsiazki").value = ksiazka.idKsiazki;
    document.getElementById("tytul").value = ksiazka.tytul || "";
    document.getElementById("autor").value = ksiazka.autor || "";
    document.getElementById("opis").value = ksiazka.opis || "";
    document.getElementById("ocena").value = ksiazka.ocena || "";
}

async function usunKsiazke(id) {
    if (!confirm("Czy na pewno usunąć tę książkę?")) {
        return;
    }

    const odp = await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    if (!odp.ok) {
        alert("Nie udało się usunąć książki");
        return;
    } else {	
		alert("Książka została usunięta");
	}

    ladujKsiazki();
}

async function wyszukajKsiazki() {
    const fraza = document.getElementById("wyszukajFraze").value.trim();

    if (!fraza) {
        ladujKsiazki();
        return;
    }

    const odp = await fetch(`${API_URL}/search?fraza=${encodeURIComponent(fraza)}`);
    const ksiazki = await odp.json();
    wyswietlKsiazki(ksiazki);
}

function wyswietlKsiazki(ksiazki) {
    const listaKsiazek = document.getElementById("listaKsiazek");
    listaKsiazek.innerHTML = "";

    if (!ksiazki.length) {
        listaKsiazek.innerHTML = "<p>Brak książek do wyświetlenia.</p>";
        return;
    }

    ksiazki.forEach(ksiazka => {
        const karta = document.createElement("div");
        karta.className = "ksiazka-karta";

        karta.innerHTML = `
            <h3>${ksiazka.tytul}</h3>
            <p><strong>Autor:</strong> ${ksiazka.autor}</p>
            <p><strong>Opis:</strong> ${ksiazka.opis|| "-"}</p>
            <p><strong>Ocena:</strong> ${ksiazka.ocena || "-"}</p>
            <button onclick="edytujKsiazke(${ksiazka.idKsiazki})">Edytuj</button>
            <button onclick="usunKsiazke(${ksiazka.idKsiazki})">Usuń</button>
        `;

        listaKsiazek.appendChild(karta);
    });
}
