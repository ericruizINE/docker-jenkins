import jenkins.model.*
import hudson.model.*

def jobName = "Publicacion" // Nombre del job
def repoUrl = "https://github.com/ericruizINE/descargaCSV.git" // URL del repositorio
def branchName = "main" // Rama del repositorio

def instance = Jenkins.getInstance()

if (instance.getJob(jobName) == null) {
    println("Creating new pipeline job: ${jobName}")

    // Crear el pipeline job
    def pipelineJob = instance.createProject(org.jenkinsci.plugins.workflow.job.WorkflowJob, jobName)

    // Configurar la definición del pipeline para que use un Jenkinsfile desde el repositorio Git
    def scm = new hudson.plugins.git.GitSCM(repoUrl)
    scm.branches = [new hudson.plugins.git.BranchSpec("*/${branchName}")]
    pipelineJob.setDefinition(new org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition(scm, "Jenkinsfile"))

    pipelineJob.save()
    println("Pipeline job ${jobName} created successfully with Jenkinsfile from ${repoUrl}.")
} else {
    println("Pipeline job ${jobName} already exists.")
}
