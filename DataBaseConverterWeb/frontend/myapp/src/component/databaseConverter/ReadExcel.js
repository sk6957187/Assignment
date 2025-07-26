import React, { useState } from 'react';
import * as XLSX from 'xlsx';
import Result from './Result';

const ReadExcel = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [excelData, setExcelData] = useState([]);
    const [link, setLink] = useState('');
    const [showDetails, setShowDetails] = useState(false);

    const parseExcelFile = (file) => {
        const reader = new FileReader();
        reader.onload = (evt) => {
            try {
                const arrayBuffer = evt.target.result;
                const workbook = XLSX.read(arrayBuffer, { type: 'array' });
                const sheetName = workbook.SheetNames[0];
                const worksheet = workbook.Sheets[sheetName];
                // const MAX_ROWS = 20000; // Limit to avoid memory crash
                const rawData = XLSX.utils.sheet_to_json(worksheet, { header: 1, defval: "" });  //read all data
                // const limitedData = rawData.slice(0, MAX_ROWS); // Read only first 10,000 rows because os not support in loaded all data
                setExcelData(rawData);
                setShowDetails(true);
            } catch (error) {
                console.error("Error reading Excel file:", error);
                alert("Failed to load Excel file. Try a smaller file or reduce rows.");
            }
        };

        reader.readAsArrayBuffer(file);

    };

    const handleFileUpload = async (e) => {
        e.preventDefault();

        if (!selectedFile && !link.trim()) {
            alert('Select a file or enter a link to an Excel file.');
            return;
        }

        if (selectedFile || link.trim()) {
            try {
                let finalLink = link.trim();

                // Convert Google Sheets view link to direct download link
                if (finalLink.includes('docs.google.com/spreadsheets/d/')) {
                    const fileId = finalLink.split('/d/')[1].split('/')[0];
                    finalLink = `https://docs.google.com/spreadsheets/d/${fileId}/export?format=xlsx`;
                } else if (finalLink.includes('drive.google.com/file/d/')) {
                    const fileId = finalLink.split('/d/')[1].split('/')[0];
                    finalLink = `https://drive.google.com/uc?id=${fileId}&export=download`;
                }

                // const response = await fetch(finalLink);
                // const blob = await response.blob();
                // const file = new File([blob], 'uploaded_file.xlsx', { type: blob.type });
                // setSelectedFile(file);
                // parseExcelFile(file);

                const formData = new FormData();
                formData.append("file", selectedFile);
                formData.append("link", finalLink);
                try {
                    const response = await fetch("http://localhost:8080/databaseconvert/uploadfile", {
                        method: "POST",
                        body: formData,
                    });
                    if (!response.ok) throw new Error("Failed to upload file");
                    const responseData = await response.json();
                    console.log("Response data: "+responseData);
                    
                    setExcelData(responseData.data); // Adjust based on your backend JSON structure
                    setShowDetails(true);
                } catch (error) {
                    console.error('Error uploading Excel file:', error);
                    alert('Failed to upload Excel file');   
                }


                return;
            } catch (error) {
                console.error('Error fetching Excel file:', error);
                alert('Failed to fetch Excel file');
                return;
            }
        }

        // File was selected from disk
        // parseExcelFile(selectedFile);
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];

        setSelectedFile(file);
        setLink("");
        document.getElementById("link").value = "";
        setShowDetails(false);
        setExcelData([]);
    };

    const handleFileLink = (e) => {
        document.getElementById("excelFile").value = null;
        setLink(e.target.value.trim());
        setSelectedFile(null);
        document.getElementById("excelFile").value = null;
        setShowDetails(false);
        setExcelData([]);
    };

    const showExcelFileContent = () => (
        <>
            <div>
                <h2>Excel File Content</h2>
                <p><strong>File Name:</strong> {selectedFile.name}</p>
                <p><strong>File Size:</strong> {selectedFile.size} bytes</p>
                <p><strong>File Type:</strong> {selectedFile.type}</p>
            </div>

            <table className="table table-bordered mt-3">
                <thead className="table-dark">
                    <tr>
                        <th>SNO</th>
                        {excelData[0]?.map((header, index) => (
                            <th key={index}>{header}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {excelData.slice(1).map((row, rowIndex) => (
                        <tr key={rowIndex}>
                            <td>{rowIndex+1}</td>
                            {row.map((cell, colIndex) => (
                                <td key={colIndex}>{cell}</td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
    console.log("Excel data: "+ excelData);
    return (
        
        <>
            <form onSubmit={handleFileUpload}>
                <div>
                    <label htmlFor="excelFile">Upload Excel File</label>
                    <input
                        type="file"
                        accept=".xlsx, .xls"
                        id="excelFile"
                        onChange={handleFileChange}
                        className="form-control"
                    />
                </div>

                <div className="mt-3">
                    <label htmlFor="link">Or enter a link to an Excel file:</label>
                    <input
                        type="text"
                        id="link"
                        value={link}
                        onChange={handleFileLink}
                        placeholder="Enter Google Sheets or Drive link"
                        className="form-control"
                    />
                </div>

                <button type="submit" className="btn btn-primary ms-3 mb-3 mt-3">Upload</button>
            </form>

            {/* {showDetails && selectedFile && showExcelFileContent()} */}
            {showDetails && selectedFile && <Result data={excelData} tableName={selectedFile.name.split('.')[0]} />}
        </>
    );
};

export default ReadExcel;
