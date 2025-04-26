package life.sm4rt.website;

import one.jpro.platform.routing.Response;
import one.jpro.platform.routing.Route;
import one.jpro.platform.routing.RouteApp;

public class App extends RouteApp {

    @Override
    public Route createRoute() {
        return Route.empty()
                .and(Route.get("/", r -> Response.node(new PageHome())));
    }
}