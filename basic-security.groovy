#!groovy

import jenkins.model.*
import hudson.security.*
import hudson.security.csrf.DefaultCrumbIssuer

def instance = Jenkins.getInstance()

// Setup admin user if not already created
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount('admin', 'password')
instance.setSecurityRealm(hudsonRealm)

// Enable Full Control Once Logged In
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()

// Disable CLI over Remoting
instance.getDescriptor("jenkins.CLI").get().setEnabled(false)