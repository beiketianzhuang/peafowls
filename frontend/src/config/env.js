let baseUrl = '';


if (process.env.NODE_ENV == 'development') {
    baseUrl = 'http://localhost:8081';

}else if(process.env.NODE_ENV == 'production'){
    baseUrl = 'localhost:8081';
}

export {
    baseUrl,
}