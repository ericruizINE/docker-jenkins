import jenkins.model.*
import hudson.tools.*
import ru.yandex.qatools.allure.jenkins.tools.*  // Importar clases específicas de Allure

def instance = Jenkins.getInstance()

// Definir la instalación de Allure Commandline
def allureDescriptor = instance.getDescriptorByType(AllureCommandlineInstallation.DescriptorImpl.class)
def installations = []

// Crear una instalación de Allure con nombre y ruta personalizada
def allureInstall = new AllureCommandlineInstallation(
    "ALLUREHOME",  // Nombre de la instalación
    "/opt/allure-2.30.0",  // Ruta de instalación
    null  // No se requieren propiedades adicionales
)

installations += allureInstall

// Guardar la configuración
allureDescriptor.setInstallations(installations.toArray(new AllureCommandlineInstallation[0]))
allureDescriptor.save()

println("Allure Commandline configured successfully!")