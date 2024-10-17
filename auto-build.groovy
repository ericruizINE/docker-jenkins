import jenkins.model.*
import hudson.model.*

// Nombre del pipeline a construir
def jobName = "Publicacion"

// Obtener la instancia de Jenkins
def instance = Jenkins.getInstance()

// Buscar el job/pipeline
def job = instance.getItem(jobName)

if (job != null) {
    println("Building pipeline: ${jobName}")
    
    // Disparar la construcción del pipeline
    job.scheduleBuild2(0)
} else {
    println("Pipeline ${jobName} no encontrado.")
}
