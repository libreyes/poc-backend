role(:app){ ["178.79.188.31"] }
role(:web){ ["178.79.188.31"] }
role :db, "178.79.188.31", :primary => true

set :environment, :uat

set :scm, "git"
set :branch, "master"
set :repository, 'https://alex.kinnane:password@gitbucket.headlondon.com/git/openeyes/openeyes-experimental-api.git'
set :deploy_to, "/home/openeyes/public_html/#{environment}/api/"
set :user, "openeyes" # the ssh user we'll deploy as.


set :tomcat_manager, "tomcat"
set :tomcat_manager_password, "IeChooP6uath"