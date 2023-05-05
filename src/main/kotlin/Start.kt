import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.Entity
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess.Export

/**
 * Start.java
 *
 * Copyright (c) 2023, yanfeiwuji.
 * All rights reserved.
 *
 * Author: yanfeiwuji
 * Created: 4月 28, 2023
 */

class Hello {
    @Export
    var name: String = "yanfeiwuji"

    @Export
    fun hello(name: String): String {
        return "hello $name"
    }

    @Export
    fun getAll(): Map<String, Any> {
        return mapOf(
            "name" to "yanfeiwuji",
            "age" to 12
        )
    }
}

@Path("/start")

@Produces(MediaType.APPLICATION_JSON)
final class Start {

    @Inject
    lateinit var sysUserRepo: SysUserRepo;

    @GET
    fun get() = sysUserRepo.findAll().list()


    @POST
    fun post() = sysUserRepo.findAll().list()

    @Path("/tt")
    @GET
    fun tt(): String {
        val context = Context
            .newBuilder("js")
            .allowAllAccess(true)
            .build()

        context.getBindings("js").putMember("hello", Hello().getAll())

        context.eval("js", "console.log(hello.name)")

        return "1ss23"
    }
}


@Entity
class SysUser : PanacheEntity() {
    var name: String? = null
    var age: Int? = null
}

@ApplicationScoped
class SysUserRepo : PanacheRepository<SysUser>