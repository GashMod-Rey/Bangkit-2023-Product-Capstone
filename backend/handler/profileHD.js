const { nanoid } = require('nanoid');
const profiles = require('../data/profiles');

const addProfileHandler = (request, h) => {
    // const { name, year, author, summary, publisher, pageCount, readPage, reading} = request.payload;
    const { name, address, telp, email, age} = request.payload;

    const id = nanoid(16);
    
    if (name === undefined){
        const response = h.response({
            status: 'fail',
            message: 'Failed, name undefined', 
        });
        response.statusCode = 400;
        return response;
    } 

    const newProfiles = {
        id, name, address, telp, email, age
    }

    profiles.push(newProfiles);

    const isSuccess = profiles.filter((profile) => profile.id === id).length > 0;
 
    if (isSuccess) {
        const response = h.response({
            status: 'success',
            message: 'Success',
            data: {
                applicantId: id,
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
module.exports = addProfileHandler;
