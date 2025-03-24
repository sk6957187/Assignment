document.getElementById('inputForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const newData = {
        name: formData.get('name'),
        age: formData.get('age'),
        dob: formData.get('dob'),
        address: formData.get('address'),
        image: formData.get('image') ? await readFileAsDataURL(formData.get('image')) : '',
        video: formData.get('video') ? await readFileAsDataURL(formData.get('video')) : '',
        audio: formData.get('audio') ? await readFileAsDataURL(formData.get('audio')) : '',
        textFile: formData.get('textFile') ? formData.get('textFile').name : 'No file'
    };

    addRowToTable(newData);
    event.target.reset();
    uploadData(newData);
});

function readFileAsDataURL(file) {
    return new Promise((resolve) => {
        if (!file) return resolve('');
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.readAsDataURL(file);
    });
}

function addRowToTable(data) {
    const tableContainer = document.getElementById('tableContainer');
    const tableBody = document.getElementById('dataTable').querySelector('tbody');
    tableContainer.style.display = 'block';
    const row = `<tr>
        <td>${data.name}</td>
        <td>${data.age}</td>
        <td>${data.dob}</td>
        <td>${data.image ? `<img src="${data.image}" width="50">` : 'No Image'}</td>
        <td>${data.video ? `<a href="${data.video}" target="_blank">View Video</a>` : 'No Video'}</td>
        <td>${data.audio ? `<audio controls src="${data.audio}"></audio>` : 'No Audio'}</td>
        <td>${data.textFile}</td>
        <td>${data.address}</td>
    </tr>`;
    tableBody.innerHTML += row;
}

async function uploadData(data) {
    try {
        const response = await fetch("http://localhost:8080/sql/add-data", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify([data])
        });
        if (response.ok) {
            const message = await response.text();
            alert(message);
        }
    } catch (error) {
        console.error("Error adding row:", error);
        alert("Error adding row: " + error.message);
    }
}
