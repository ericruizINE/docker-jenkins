import jenkins.model.*
import hudson.model.*
import javaposse.jobdsl.plugin.ExecuteDslScripts

// Nombre del nuevo pipeline
def jobName = "Publicacion"

// Verificar si el job ya existe
def instance = Jenkins.getInstance()
if (instance.getJob(jobName) == null) {
    println("Creating new pipeline job: ${jobName}")

    def pipelineJob = instance.createProject(org.jenkinsci.plugins.workflow.job.WorkflowJob, jobName)

    // Definir el script del pipeline
    def script = """
    pipeline {
        agent any
        stages {
            stage('Clone Repository') {
                steps {
                    git url: 'https://github.com/ericruizINE/descargaCSV.git', branch: 'main'
                }
            }
        }
    }
    """.stripIndent()

    pipelineJob.setDefinition(new org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition(script, true))
    pipelineJob.save()
    println("Pipeline job ${jobName} created successfully.")
} else {
    println("Pipeline job ${jobName} already exists.")
}
