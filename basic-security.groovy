#!groovy

import jenkins.model.*
import hudson.security.*
import hudson.util.*
import jenkins.install.*

def instance = Jenkins.getInstance()

// Crear usuario admin con contraseña predefinida
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount('admin','password') 
instance.setSecurityRealm(hudsonRealm)

// Configurar permisos de seguridad
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)

// Marcar instalación como completada
instance.setInstallState(InstallState.INITIAL_SETUP_COMPLETED)

instance.save()