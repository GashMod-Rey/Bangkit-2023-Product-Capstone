// const { 
//     addBookHandler, 
//     getAllBooksHandler, 
//     getBookByIdHandler, 
//     editBookByIdHandler, 
//     deleteBookByIdHandler } = require("./handler");

const { addApplicantsHandler } = require("./handler/handler-applicants");
const { addCompaniesHandler } = require("./handler/handler-company");

const routes = [
    {
        method: 'POST',
        path: '/applicants',
        handler: addApplicantsHandler,
    },
    {
        method: 'POST',
        path: '/companies',
        handler: addCompaniesHandler,
    },

    // {
    //     method: 'GET',
    //     path: '/books',
    //     handler: getAllBooksHandler,
    // },
    // {
    //     method: 'GET',
    //     path: '/books/{id}',
    //     handler: getBookByIdHandler,
    // },
    // {
    //     method: 'PUT',
    //     path: '/books/{id}',
    //     handler: editBookByIdHandler,
    // },
    // {
    //     method: 'DELETE',
    //     path: '/books/{id}',
    //     handler: deleteBookByIdHandler,
    //   },
  ];
   
module.exports = routes;