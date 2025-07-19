import React, { useState, useEffect } from 'react';
import * as XLSX from 'xlsx';
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

const Result = ({ data, tableName }) => {
    const [sqldata, setSqlData] = useState(data);
    const [filterText, setFilterText] = useState("");

    useEffect(() => {
        setSqlData(data);
    }, [data]);

    const handleFilter = (e) => {
        const text = e.target.value.toLowerCase();
        setFilterText(text);
        const filtered = data.filter((row) =>
            Object.values(row).some((value) =>
                String(value).toLowerCase().includes(text)
            )
        );
        if (filtered.length > 0) {
            setSqlData(filtered);
        }
    };


    const highlightMatch = (text) => {
        if (!filterText) return text;

        const lowerText = text.toLowerCase();
        const index = lowerText.indexOf(filterText);
        if (index === -1) return text;

        const before = text.substring(0, index);
        const match = text.substring(index, index + filterText.length);
        const after = text.substring(index + filterText.length);

        return (
            <>
                {before}
                <mark style={{ backgroundColor: 'red', padding: '0 2px', borderRadius: '10px' }}>{match}</mark>
                {after}
            </>
        );
    };

    const downloadCSV = () => {
        let fileName = prompt("Enter file name", "data.csv");
        if (!fileName || fileName.trim() === "") {
            fileName = "data.csv";
        }
        if (!fileName.toLowerCase().endsWith(".csv")) {
            fileName += ".csv";
        }
        const csvContent =
            "data:text/csv;charset=utf-8," +
            [headers.join(","), ...sqldata.map(row => headers.map(h => row[h]).join(","))].join("\n");

        const link = document.createElement("a");
        link.setAttribute("href", csvContent);
        link.setAttribute("download", fileName);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    const downloadExcel = () => {
        let fileName = prompt("Enter file name", "data.xlsx");

        // If user clicks cancel or leaves it empty
        if (!fileName || fileName.trim() === "") {
            fileName = "data.xlsx";
        }
        if (!fileName.toLowerCase().endsWith(".xlsx")) {
            fileName += ".xlsx";
        }
        const worksheet = XLSX.utils.json_to_sheet(sqldata);
        const workbook = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(workbook, worksheet, "Data");
        XLSX.writeFile(workbook, fileName);
    };


    const downloadPDF = () => {
        let fileName = prompt("Enter file name", "data.pdf");
        if (!fileName || fileName.trim() === "") {
            fileName = "data.pdf";
        }
        if (!fileName.toLowerCase().endsWith(".pdf")) {
            fileName += ".pdf";
        }

        const tableRows = sqldata.map(row => headers.map(h => row[h]));
        // Use landscape if too wide
        const estimatedTableWidth = headers.length * 30;    // Estimate width per column (30mm per column)
        const portraitPageWidth = 210 - 20; // A4 width - margins (~10mm each side)
        const orientation = estimatedTableWidth > portraitPageWidth ? "landscape" : "portrait";

        // Create jsPDF instance with the determined orientation
        const doc = new jsPDF({ orientation });

        doc.text("Data Table", 14, 10);
        autoTable(doc, {
            head: [headers],
            body: tableRows,
            startY: 20,
            theme: "striped",
            styles: {
                fontSize: 8,
                cellWidth: "wrap",
            },

        });
        // trigger download
        doc.save(fileName);

        // Open in new tab
        const pdfBlob = doc.output("blob");
        const blobUrl = URL.createObjectURL(pdfBlob);
        window.open(blobUrl, "_blank");

    };

    const uploadSql = () => {
        let name = tableName || prompt("Enter SQL table name");
        if (!name || name.trim() === "") {
            alert("Table name is required.");
            return;
        }

        const formData = new FormData();
        formData.append("data", JSON.stringify(sqldata));
        formData.append("tableName", name);

        fetch("http://localhost:8080/databaseconvert/uploadSql", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                data: sqldata,
                tableName: name
            })
        })
            .then(res => {
                if (res.ok) {
                    alert("Data uploaded successfully");
                } else {
                    alert("Failed to upload data");
                }
            })
            .catch(err => {
                console.error("Error uploading data:", err);
                alert("Error uploading data");
            });

    };




    if (!sqldata) return <div>No data available</div>;
    console.log("table name: ", tableName);
    const headers = Object.keys(sqldata[0]);
    return (
        <>
            <div className="table-responsive">
                <div className="d-flex justify-content-between align-items-center mb-2">
                    <div className="d-flex">
                        <label htmlFor="filter" className="form-label me-2 mt-2">Filter:</label>
                        <input
                            type="text"
                            className="form-control w-50"
                            id="filter"
                            placeholder="Enter filter text"
                            value={filterText}
                            onChange={handleFilter}
                        />
                    </div>

                    <div>
                        <button className="btn btn-outline-primary me-2" onClick={uploadSql}>Upload in SQL</button>
                        <button className="btn btn-outline-primary me-2" onClick={downloadCSV}>Download CSV</button>
                        <button className="btn btn-outline-success me-2" onClick={downloadExcel}>Download Excel</button>
                        <button className="btn btn-outline-danger" onClick={downloadPDF}>Download PDF</button>
                    </div>
                </div>

                <table className="table table-bordered">
                    <thead className="table-dark">
                        <tr>
                            <th>SNO.</th>
                            {headers.map(header => (
                                <th key={header}>{header.toUpperCase()}</th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {sqldata.map((row, rowIndex) => (
                            <tr key={rowIndex}>
                                <td>{rowIndex + 1}</td>
                                {headers.map(header => (
                                    <td key={header}>{highlightMatch(String(row[header]))}</td>
                                ))}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </>
    );
};

export default Result;
