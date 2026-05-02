const express = require('express');
const app = express();
const port = 3000;

const config = {
    host: 'db',
    user: 'root',
    password: '"root"',
    database: 'nodedb'
};
const mysql = require('mysql');

const connection = mysql.createConnection(config);

// Function to fetch all names and IP addresses from the database
const getAllNamesAndIPs = () => {
    return new Promise((resolve, reject) => {
        const sql = 'SELECT name, ip_address FROM people';
        connection.query(sql, (error, results) => {
            if (error) {
                reject(error);
            } else {
                const data = results.map(row => ({ name: row.name, ip_address: row.ip_address }));
                resolve(data);
            }
        });
    });
};

// Function to insert a new name and IP address into the database
const insertNameAndIP = (name, ip_address) => {
    return new Promise((resolve, reject) => {
        const sql = `INSERT INTO people(name, ip_address) VALUES('${name}', '${ip_address}')`;
        connection.query(sql, (error, results) => {
            if (error) {
                reject(error);
            } else {
                resolve();
            }
        });
    });
};

app.get('/', async (req, res) => {
    try {
        // Fetch all names and IP addresses from the database
        const namesAndIPs = await getAllNamesAndIPs();
        
        // Generate a new name
        const newName = 'Name ' + (namesAndIPs.length + 1);
        
        // Get the actual IP address of the client
        const clientIP = req.ip;
        
        // Insert the new name and client's IP address into the database
        await insertNameAndIP(newName, clientIP);

        // Send the list of names and IP addresses as a response
        let htmlResponse = '<h1>Full Cycle</h1>';
        htmlResponse += '<ul>';
        namesAndIPs.forEach(data => {
            htmlResponse += `<li>${data.name}</li>`;
            //htmlResponse += `<li>${data.name} - ${data.ip_address}</li>`;
        });
        htmlResponse += '</ul>';
        res.send(htmlResponse);
    } catch (error) {
        res.status(500).send('Error: ' + error.message);
    }
});

app.listen(port, () => {
    console.log('Rodando na porta ' + port);
});
