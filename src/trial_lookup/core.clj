(ns trial-lookup.core
  (:use compojure.core hiccup.core)
  (:require [clj-http.client :as client]))

(defn url-for-chictr
  [id]
  (let [search-result (client/post "http://www.chictr.org/Site/Search.aspx?lang=EN")]
    ""))
    ;(scrape (:body search-result) "link with href starting TaskView.aspx?ID=")))

(defn url-for-irct
  [id]
  (let [search-result (client/get "http://www.irct.ir/searchen.php?keyword=" id "&field=a&lang=en")]
    ""))
    ;(scrap (:body search-result) "link with nested text 'More details...'")))

(def lookups
     [{:scheme "ANZCTR"
       :org-name "Australian New Zealand Clinical Trials Registry"
       :org-url "http://www.anzctr.org.au/"
       :id-re #"ACTRN\d{14}"
       :get-url #(str "http://www.anzctr.org.au/" %) }
      {:scheme "ChiCTR"
       :org-name "Chinese Clinical Trial Register"
       :id-re #"ChiCTR\-[A-Z]{3}\-\d{8}"
       :get-url url-for-chictr }
      {:scheme "DRKS"
       :org-name "German Clinical Trials Register"
       :id-re #"DRKS\d{8}"
       :get-url #(str "https://drks-neu.uniklinik-freiburg.de/drks_web/navigate.do?navigationId=trial.HTML&TRIAL_ID=" %) }
      {:scheme "CTRI"
       :org-name "Clinical Trials Registry - India"
       :id-re #"CTRI/\d{4}/\d{3}/\d{6}"
       :get-url "" }
      {:scheme "Provisional CTRI"
       :org-name "Clinical Trials Registry - India"
       :id-re #"PROVCTRI/\d{4}/\d{3}/\d{6}"
       :get-url "" }
      {:scheme "IRCT"
       :org-name "Iranian Registry of Clinical Trials"
       :id-re #"IRCT\d{12}\s\d"
       :get-url url-for-irct }
      {:scheme "JMA-IIA"
       :org-name "The Centre for Clinical Trials, Japan Medical Association Clinical Trail Register"
       :id-re #"JMA-IIA\d{5}"}
      {:scheme "JapicCTI"
       :org-name "The Japan Pharmaceutical Information Centre"
       :id-re #"JapicCTI\-\d{6}"}
      {:scheme "UMIN"
       :org-name "University Hospital Medical Information Network Clinical Trails Registry"
       :id-re #"(UMIN)|C\d{9}"}
      {:scheme "NTR"
       :org-name "The Netherlands National Trial Register"
       :id-re #"NTR\d{2,4}"}
      {:scheme "SLCTR"
       :org-name "Sri Lanka Clinical Trials Registry"
       :id-re #"SLCTR/\d{4}/\d{3}"}
      {:scheme "ISRCTN"
       :org-name "ISRCTN"
       :id-re #"ISRCTN\d{8}"
       :get-url #(str "http://isrctn.org/" %)}
      {:scheme "NCT"
       :org-name "ClinicalTrials.gov"
       :org-url "http://clinicaltrials.gov"
       :id-re #"NCT\d{8}"
       :get-url #(str "http://www.clinicaltrials.gov/ct2/show/" %)}])

(defn lookup-scheme
  [id]
  (first (filter #(re-matches id (:id-re %)) lookups)))

(defn lookup-url
  [id]
  (let [get-url-fn (:get-url (lookup-scheme id))]
    (get-url-fn id)))

(defn show-index
  []
  (html5 [:h1 "Clinical Trial Lookup"]))

(defn show-trial
  [id]
  (redirect (lookup-url id)))

(defn show-registries
  []
  ())

(defn show-json-trial
  [id]
  ())

(defroutes routes
  (GET "/" [] (show-index))
  (GET "/trial/:id" [id] (show-trial id))
  (GET "/registry" [] (show-registries))
  (GET "/api/trial/:id" [id] (show-json-trial id)))