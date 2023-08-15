db.createUser({
    user: 'root',
    pwd: 'toor',
    roles: [
        {
            role: 'readWrite',
            db: 'testDB',
        },
    ],
});
db.createCollection('app_users', { capped: false });

db.app_users.insert([
{ 
    "username": "ragnar777", 
    "dni": "VIKI771012HMCRG093", 
    "enabled": true, 
    "password": "$2a$10$LAeI7pGUQwmEbCrZx1N82OdffdXYv6d48dbzroM09MScpXz6KjpFi", 
    "role": 
    {
        "granted_authorities": ["ROLE_USER"]
    } 
},
{ 
    "username": "heisenberg", 
    "dni": "BBMB771012HMCRR022", 
    "enabled": true, 
    "password": "$2a$10$xXwTPyEFMC1.u9LWSKkj2uIZFvvOYr3nid5OR8llXBEjuIEBrjRra", 
    "role": 
    {
        "granted_authorities": ["ROLE_USER"]
    } 
},
{ 
    "username": "misterX", 
    "dni": "$2a$10$aL7qTSu0eAKCWh9Pxz8JjeXOxh94EAS1fjFHn/Z0P4Ymxm/XCphRq", 
    "enabled": true, 
    "password": "misterX123", 
    "role": 
    {
        "granted_authorities": ["ROLE_USER", "ROLE_ADMIN"]
    } 
},
{ 
    "username": "neverMore", 
    "dni": "WALA771012HCRGR054", 
    "enabled": true, 
    "password": "$2a$10$KtY71v8mToOWKiJhK/qcseU1n57pmEHbB72LJnrEpDOlz.LK0cUUq", 
    "role": 
    {
        "granted_authorities": ["ROLE_ADMIN"]
    } 
}
]);