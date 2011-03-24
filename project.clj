(defproject trial-lookup "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.2.1"]
		 [compojure "0.6.2"]
		 [hiccup "0.3.4"]
		 [clj-http "0.1.2"]]
  :dev-dependencies [[lein-ring "0.4.0"]]
  :ring {:handler trial-lookup.core/routes})
