package httpserver.response;

import httpserver.utils.StatusCodes;
import httpserver.utils.constants;
import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;

public class ResponseWriter implements IResponseWriter {

    public Response buildSuccessResponse(Request request) {
        Response response;
        if (request.path.equals("/simple_get_with_body")) {
            response = new ResponseBuilder()
                    .setStatusCode(StatusCodes.SUCCESS)
                    .addHeader("Content-Type", "text/html")
                    .setBody("Hello world")
                    .build();

        } else {
            response = new ResponseBuilder()
                    .setStatusCode(StatusCodes.SUCCESS)
                    .addHeader("Content-Type", "text/html")
                    .build();
        }
        return response;
    }

    public Response buildPageNotFoundResponse(Request request) {
        Response response = new ResponseBuilder()
                .setStatusCode(StatusCodes.PAGE_NOT_FOUND)
                .build();
        return response;
    }

    public String formatResponse(Response response) {
        String formattedResponse;
        String statusLine = getStatusLine(response);
        if (response.body == null) {
            formattedResponse = statusLine;
        } else {
            formattedResponse = statusLine + response.body;
        }
        return formattedResponse;
    }

    public String getStatusLine(Response response) {
        return response.protocol + " " + response.statusCode + constants.LINE_BREAK;
    }
}
