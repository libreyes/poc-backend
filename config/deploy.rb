require "capistrano/ext/multistage"
require "capistrano_colors"

set :application, "openeyes"

set :keep_releases, 2 # number of deployed releases to keep
set :use_sudo, false
default_run_options[:pty] = true
set :deploy_via, :remote_cache

namespace :deploy do
  desc <<-DESC
  Does a clean deploy by removing the cached-copy folder first,
  then runs deploy.full
  DESC

  desc <<-DESC
  Does a full deploy using the tasks specified.
  DESC
  task :full do
    transaction do
      remove_cached_copy
      update_code
      #clean_release
      build
      upload_war
      create_symlink
    end
      # We are not able to roll back while using the Tomcat manager as
      # capistrano only knows about file operations.
      undeploy_app
      deploy_app
  end

  desc <<-DESC
  Removes the cached-copy folder.
  DESC
  task :remove_cached_copy do
    run("rm -rf #{deploy_to}/shared/cached-copy")
    #run("mkdir #{deploy_to}/shared/cached-copy")
  end
#IeChooP6uath

  desc <<-DESC
  Clean out release directory
  DESC
  task :clean_release do
    run("rm -rf #{release_path}/*")
  end

  task :build do
    puts "==================Building with SBT======================" # Line 22
    `./sbt clean package`
  end

  desc <<-DESC
  Upload war to server.
  DESC
  task :upload_war do
    top.upload("target/scala-2.11/openeyes_2.11-0.1.4.war",
               "#{release_path}/openeyes_2.11-0.1.4.war")
  end

  desc <<-DESC
  Upload war to server.
  DESC
  task :undeploy_app do
    puts "==================Undeploy war======================"#Line 24
    run "curl --user #{tomcat_manager}:#{tomcat_manager_password} http://localhost:8080/manager/text/undeploy?path=/ROOT"
  end

  desc <<-DESC
  Upload war to server.
  DESC
  task :deploy_app do
    puts "==================Deploy war to Tomcat======================" #Line 26
    run "curl --upload-file #{current_path}/openeyes_2.11-0.1.4.war --user #{tomcat_manager}:#{tomcat_manager_password} http://localhost:8080/manager/text/deploy?path=/ROOT"
  end
end