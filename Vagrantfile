# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  # The hostname the machine should have. Defaults to nil. If nil, Vagrant won't
  # manage the hostname. If set to a string, the hostname will be set on boot.
  #
  config.vm.hostname = "openeyes-vagrant"

  # A message to show after vagrant up. This will be shown to the user and is
  # useful for containing instructions such as how to access various components
  # of the development environment.
  #
  config.vm.post_up_message = "Up completed. Now ssh on and run sbt container:start"

	# All Vagrant configuration is done here. The most common configuration
  # options are documented and commented below. For a complete reference,
  # please see the online documentation at vagrantup.com.

  # Every Vagrant virtual environment requires a box to build off of.
  #
  config.vm.box = "ubuntu/trusty64"

  # The url from where the 'config.vm.box' box will be fetched if it
  # doesn't already exist on the user's system.
  #
  # config.vm.box_url = "http://files.vagrantup.com/precise64.box"

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine. In the example below,
  # accessing "localhost:8080" will access port 8080 on the guest machine.
  #
  config.vm.network :forwarded_port, host: 8080, guest: 8080
  config.vm.network :forwarded_port, host: 11112, guest: 11112

  # Create a private network, which allows host-only access to the machine
  # using a specific IP.
  #
  # config.vm.network :private_network, ip: "192.168.33.10"

  # Create a public network, which generally matched to bridged network.
  # Bridged networks make the machine appear as another physical device on
  # your network.
  #
  # config.vm.network :public_network

  # If true, then any SSH connections made will enable agent forwarding.
  # Default value: false
  #
  config.ssh.forward_agent = true

  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  #
	config.vm.synced_folder ".", "/app"

  # Provider-specific configuration so you can fine-tune various
  # backing providers for Vagrant. These expose provider-specific options.
  # Example for VirtualBox:
  #
	config.vm.provider :virtualbox do |vb|
		vb.memory = 2048
		vb.cpus = 2
	end

  # Provision for shell
  #
  config.vm.provision :shell, path: "vagrant/bootstrap.sh"
end
