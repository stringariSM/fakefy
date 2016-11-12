package web.ucs.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.intf.DadoBinarioDAO;
import web.ucs.dao.postgres.PostgresDAOFactory;
import web.ucs.model.DadoBinario;

/**
 * The Image servlet for serving from database.
 * 
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/04/imageservlet.html
 */
@SuppressWarnings("serial")
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

	// Properties
	// ---------------------------------------------------------------------------------

	private DAOFactory factory = new PostgresDAOFactory();

	// Init
	// ---------------------------------------------------------------------------------------

	public void init() throws ServletException {
	}

	// Actions
	// ------------------------------------------------------------------------------------

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Get ID from request.
		String sId = request.getParameter("id");

		// Check if ID is supplied to the request.
		if (sId == null) {
			// Do your thing if the ID is not supplied to the request.
			// Throw an exception, or send 404, or show default/warning image,
			// or just ignore it.
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		Integer id = null;

		try {
			id = Integer.parseInt(sId);
		} catch (NumberFormatException nfe) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		// Lookup Image by ImageId in database.
		// Do your "SELECT * FROM Image WHERE ImageID" thing.
		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		DadoBinario dado;
		try {
			dado = dao.buscaPorId(id);
		} catch (FalhaAcessoAosDadosException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		// Check if image is actually retrieved from database.
		if (dado == null) {
			// Do your thing if the image does not exist in database.
			// Throw an exception, or send 404, or show default/warning image,
			// or just ignore it.
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		// Init servlet response.
		response.reset();
		
		
		response.setContentType(dado.getTipo());
		
		
//		response.setContentType("image/jpeg");
		
		
		response.setContentLength(dado.getConteudo().length);

		// Write image content to response.
		response.getOutputStream().write(dado.getConteudo());
	}

}