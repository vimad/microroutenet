minikube start -p microroutenet
minikube -p microroutenet docker-env | Invoke-Expression

docker-compose -p postgres16 -f postgres16.yml up -d