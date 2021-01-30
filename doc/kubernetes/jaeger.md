kubectl -n linkerd port-forward svc/linkerd-jaeger 16686
kubectl -n emojivoto port-forward svc/web-svc 8080:80


vbouchaud/jaeger-all-in-one

docker run -d --name jaeger \
-e COLLECTOR_ZIPKIN_HTTP_PORT=9411 \
-p 5775:5775/udp \
-p 6831:6831/udp \
-p 6832:6832/udp \
-p 5778:5778 \
-p 16686:16686 \
-p 14268:14268 \
-p 14250:14250 \
-p 9411:9411 \
vbouchaud/jaeger-all-in-one:1.20.0