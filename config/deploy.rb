require "capistrano/ext/multistage"
require "capistrano_colors"

set :application, "openeyes"

set :keep_releases, 3 # number of deployed releases to keep
set :use_sudo, false
default_run_options[:pty] = true
set :deploy_via, :remote_cache

namespace :deploy do
  desc <<-DESC
  Does a full deploy using the tasks specified.
  DESC

  task :full do
    transaction do
      remove_cached_copy
      update_code
      #clean_release
      backup_dev_app_conf
      copy_env_app_conf
      build
      revert_dev_app_conf
      upload_war
      create_symlink
    end
    # We are not able to roll back while using the Tomcat manager as
    # capistrano only knows about file operations.
    undeploy_app
    deploy_app
    cleanup
  end

  task :tag do
    set_tag
    full
  end

  desc <<-DESC
  Removes the cached-copy folder.
  DESC
  task :remove_cached_copy do
    run("rm -rf #{deploy_to}/shared/cached-copy")
    # run("mkdir #{deploy_to}/shared/cached-copy")
  end

  desc <<-DESC
  Clean out release directory
  DESC
  task :clean_release do
    run("rm -rf #{release_path}/*")
  end

  desc <<-DESC
  Backup the existing application.conf file in the resources folder.
  DESC
  task :backup_dev_app_conf do
    conf = get_application_conf
    `mv #{conf} #{conf}.bak`
  end

  desc <<-DESC
  Get the application.conf file for the specific env and copy to the resource
  folder.
  DESC
  task :copy_env_app_conf do
    conf = get_env_application_conf
    dest = get_resources_folder
    `cp #{conf} #{dest}`
  end

  task :build do
    puts "==================Building with SBT======================"
    `./sbt clean package`
  end

  desc <<-DESC
  Revert the application.conf file from the backup in the resources folder.
  DESC
  task :revert_dev_app_conf do
    conf = get_application_conf
    `rm #{conf}`
    `mv #{conf}.bak #{conf}`
  end

  desc <<-DESC
  Upload war to server.
    DESC
  task :upload_war do
    current_version = get_version_from_file
    top.upload("target/scala-2.11/openeyes_2.11-#{current_version}.war", "#{release_path}/openeyes_2.11--#{current_version}.war")
  end

  desc <<-DESC
  Upload war to server.
  DESC
  task :undeploy_app do
    puts "==================Undeploy war======================"
    run "curl --user #{tomcat_manager}:#{tomcat_manager_password} http://#{hostname}:8080/manager/text/undeploy?path=/"
  end

  desc <<-DESC
  Upload war to server.
  DESC
  task :deploy_app do
    current_version = get_version_from_file
    puts "==================Deploy war to Tomcat======================"
    run "curl --upload-file #{current_path}/openeyes_2.11--#{current_version}.war --user #{tomcat_manager}:#{tomcat_manager_password} http://#{hostname}:8080/manager/text/deploy?path=/ROOT"
  end

  desc <<-DESC
  Set the tag for a deploy.
  DESC
  task :set_tag do
    set :branch do
      default_tag = `git tag`.split("\n").last

      tag = Capistrano::CLI.ui.ask "Tag to deploy (make sure to push the tag first): [#{default_tag}] "
      tag = default_tag if tag.empty?
      tag
    end
  end

  def get_version_from_file
    File.open("#{File.expand_path File.dirname(__FILE__)}/../version").readlines.first
  end

  def get_resources_folder
    "#{File.expand_path(File.dirname(__FILE__))}/../src/main/resources"
  end

  def get_application_conf
    "#{get_resources_folder}/application.conf"
  end

  def get_env_application_conf
    "#{File.expand_path(File.dirname(__FILE__))}/#{environment}/application.conf"
  end
end

namespace :db do

  desc <<-DESC
  Run the import data script to reset the Mongo DB.
  DESC
  task :import_data do
    run "cd #{current_path}/docs/sample && ./import.sh -d #{environment}"
  end

end
