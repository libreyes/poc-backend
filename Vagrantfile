# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

	config.vm.box = "ubuntu/trusty64"
	config.vm.provision :shell, path: "vagrant/bootstrap.sh"
	config.vm.network :forwarded_port, host: 8081, guest: 8080
	config.vm.synced_folder ".", "/app"

	config.vm.provider "virtualbox" do |vb|
		vb.memory = 2048
		vb.cpus = 2
	end
end
