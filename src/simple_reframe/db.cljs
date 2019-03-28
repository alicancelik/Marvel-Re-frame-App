(ns simple-reframe.db
  (:require [reagent.core :as r]))


(def app-state (r/atom {:params            {:ts     1
                                            :hash   "fd0dfcceac0f25a9a2ce7d1dab82c778"
                                            :apikey "4e3ab28c4154e5b7104abe76933d3aa1"
                                            :limit  30}
                        :current-character ""
                        :active-button     ""
                        ::modal-visibility false
                        :characters        {}}))

