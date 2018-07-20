#!/usr/bin/env bash
lein with-profile test test-cljs-auto | ./only_result.sh