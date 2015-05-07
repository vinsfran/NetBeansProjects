package py.gov.mca.reclamosmca.managedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "imagenesMB")
public class ImagenesMB {

    private List<String> imagenesPortada;

    @PostConstruct
    public void init() {
        imagenesPortada = new ArrayList<String>();
        imagenesPortada.add("logo_5.jpg");
        imagenesPortada.add("logo_4.jpg");
        imagenesPortada.add("logo_3.jpg");
        imagenesPortada.add("logo_2.jpg");
        imagenesPortada.add("logo_1.jpg");
    }

    public List<String> getImagenesPortada() {
        return imagenesPortada;
    }

}
