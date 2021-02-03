package pl.krystian.springmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MapController {


    final Covid19Confirmed covid19Confirmed;
    private List<Point> pointList ;

    public MapController(Covid19Confirmed covid19Confirmed) throws IOException, InterruptedException {
        this.covid19Confirmed = covid19Confirmed;
        pointList = new ArrayList<>();
        pointList.addAll(covid19Confirmed.getPoints());
    }


    @RequestMapping(method = RequestMethod.GET)
    public String getMap(Model model){
        model.addAttribute("pointList",pointList);
        return "map";
    }

}
