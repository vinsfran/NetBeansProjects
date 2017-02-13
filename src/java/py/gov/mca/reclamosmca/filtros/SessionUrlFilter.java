package py.gov.mca.reclamosmca.filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.beansession.MbSUsuarios;

/**
 *
 * @author vinsfran
 */
@WebFilter("*.xhtml")
public class SessionUrlFilter implements Filter {

    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestUrl = req.getRequestURL().toString();

        HttpSession session = req.getSession(true);
        MbSUsuarios mbSUsuarios = (MbSUsuarios) session.getAttribute("mbSUsuarios");

        if (session.getAttribute("loginUsuario") == null && !requestUrl.contains("index.xhtml")) {
            if (requestUrl.contains("registro.xhtml")) {
                chain.doFilter(request, response);
            } else if (requestUrl.contains("login.xhtml")) {
                chain.doFilter(request, response);
            } else if (requestUrl.contains("recuperarContrasenia.xhtml")) {
                chain.doFilter(request, response);
            } else if (requestUrl.contains("contacto.xhtml")) {
                chain.doFilter(request, response);
            } else if (requestUrl.contains("mapa.xhtml")) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/faces/index.xhtml");
            }
        } else {
            //VALIDACION PARA ACCESO NO AUTORIZADO POR URL
//            if (mbSUsuarios != null) {
//                Usuarios usuario = mbSUsuarios.getUsuario();
//                List<PermisosElementosWeb> listaDePermisosDeElementos = usuario.getFkCodRol().getPermisosElementosWebList();
//                String valorRetorno = "false";
//                for (int i = 0; i < listaDePermisosDeElementos.size(); i++) {
//                    PermisosElementosWeb per = listaDePermisosDeElementos.get(i);
//                    System.out.println("ENNTRAAA222: " + per.getFkCodElementoWeb().getNombreElementoWeb());
//                    if (requestUrl.contains(per.getFkCodElementoWeb().getNombreElementoWeb())) {
//                        
//                        valorRetorno = per.getValorVisible();
//                    }
//                }
//                if (valorRetorno.equals("true")) {
//                    chain.doFilter(request, response);
//                } else {
//                    res.sendRedirect(req.getContextPath() + "/faces/admin_mis_reclamos.xhtml");
//                }
//            } else {
//                System.out.println("ENNTRAAA2224444: ");
//                chain.doFilter(request, response);
//            }

            chain.doFilter(request, response);

        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
