## _Selenium Grid using Kubernetes_

### Setting up kubernetes 
[Reference Link to set up K8 on Ubuntu](https://linuxconfig.org/how-to-install-kubernetes-on-ubuntu-20-04-focal-fossa-linux)
- All master and worker must have minimum 2 processor
- Swap should be off for all nodes in kubernetes cluster
    - To check: `free -h`
    - To turn off: `swapoff -a`
    - To avoid swap to turn on after reboot: comment or remove all swap entries from `/etc/fstab`
- Hostname should be different for all nodes
- Check UUID for all node `cat /sys/class/dmi/id/product_uuid`. All should be unique
- Install supported docker for kubernetes on node and master
    - For [ubuntu](https://docs.docker.com/engine/install/ubuntu/) 
- Setup kubernetes using `kubeadm` or any other method node and master
    - For [kubeadm](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm/) 
- Setup POD network for cluster on master using any method on master
    - for e.g. Using Flannel: `kubeadm init --pod-network-cidr=10.244.0.0/16`
    - once you run above command, you can see commands to copy some files on master and run on slave
    - **Note:** If you are getting error - `The connection to the server localhost:8080 was refused - did you specify the right host or port?` then run below commands:
      ```
          $ mkdir -p $HOME/.kube
          $ sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
          $ sudo chown $(id -u):$(id -g) $HOME/.kube/config
      ```
    - check status by `kubectl get nodes -A` and all should be `running` if not than run `kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml`  [Reference Link](https://coreos.com/flannel/docs/latest/kubernetes.html)
- Set bash auto complete for kubectl
  ```
    apt install bash-completion
    kubectl completion bash > ~/.kube/kubecom.sh
    source ~/.kube/kubecom.sh
    vim ~/.profile and paste this at last `source ~/.kube/kubecom.sh`
  ```
- To fix x509 certificate problem run `export KUBECONFIG=/etc/kubernetes/kubelet.conf`
- Set static IP for master. [Reference Link](https://www.techrepublic.com/article/how-to-configure-a-static-ip-address-in-ubuntu-server-18-04/)