import com.example.app._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    // Optional because * is the default
    context.initParameters("org.scalatra.cors.allowedOrigins") = "*"
    // Disables cookies, but required because browsers will not allow passing credentials to wildcard domains
    context.initParameters("org.scalatra.cors.allowCredentials") = false
    context.mount(new MyScalatraServlet, "/*")
  }
}
