const { nanoid } = require('nanoid');
const orgHistories = require("../data/orgHistories");

const addOrgHistoryHandler = (request, h) => {
    // const { name, year, author, summary, publisher, pageCount, readPage, reading} = request.payload;
    const { orgName, yearIn, yearOut, position, description} = request.payload;

    const id = nanoid(16);
    
    if (orgName === undefined){
        const response = h.response({
            status: 'fail',
            message: 'Failed, Organitation Name undefined', 
        });
        response.statusCode = 400;
        return response;
    } 

    const newOrgHistories = {
        id, orgName, yearIn, yearOut, position, description
    }

    orgHistories.push(newOrgHistories);

    const isSuccess = orgHistories.filter((orgHistory) => orgHistory.id === id).length > 0;
 
    if (isSuccess) {
        const response = h.response({
            status: 'success',
            message: 'Success',
            data: {
                orgHistoryId: id,
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
module.exports = addOrgHistoryHandler;