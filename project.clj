(defproject lytek "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]]
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-doo "0.1.6"]
            [lein-auto "0.1.3"]]

  :doo {:paths {:rhino "lein run -m org.mozilla.javascript.tools.shell.Main"}}
  :aliases {"test-cljs" ["with-profile" "test" "doo" "rhino" "test" "once"]
            "test-cljs-auto" ["with-profile" "test" "doo" "rhino" "test" "auto"]
            "test-auto" ["auto" "test"]
            "test-all"  ["do" ["test"] ["test-cljs"]]
            "test-all-auto" ["do" ["test-auto" "test-cljs-auto"]]}
  :profiles
  {:dev {:dependencies [[org.mozilla/rhino "1.7.7"]]}
   :test {:dependencies [[org.mozilla/rhino "1.7.7"]
                         [org.clojure/test.check "0.9.0"]]
          :cljsbuild
          {:builds
           {:test
            {:source-paths ["src" "test"]
             :compiler {:output-to "target/main.js"
                        :output-dir "target"
                        :main lytek.test-runner
                        :optimizations :whitespace}}}}}})
