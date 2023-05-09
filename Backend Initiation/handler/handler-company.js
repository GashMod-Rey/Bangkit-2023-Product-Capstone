const { nanoid } = require('nanoid');
const companies = require('../data/companies');

const addCompaniesHandler = (request, h) => {
    // const { name, year, author, summary, publisher, pageCount, readPage, reading} = request.payload;
    const { name, address, telp, email, age, education, experience, skill } = request.payload;

    const id = nanoid(16);
    
    if (name === undefined){
        const response = h.response({
            status: 'fail',
            message: 'Failed, name undefined', 
        });
        response.statusCode = 400;
        return response;
    } 

    const newCompanies = {
        id, name, address, telp, email, age, education, experience, skill
    }

    companies.push(newProfiles);

    const isSuccess = companies.filter((company) => company.id === id).length > 0;
 
    if (isSuccess) {
        const response = h.response({
            status: 'success',
            message: 'Success',
            data: {
                companyId: id,
            },
        });
        response.statusCode = 201;
        return response;
    }
    if(!isSuccess){
        const response = h.response({
            status: "fail",
            message: "Failed",
        });
        response.statusCode = 500;
        return response;
    }
}
module.exports = addCompaniesHandler;
