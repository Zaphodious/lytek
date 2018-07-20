#!/usr/bin/env bash
lein with-profile test test-auto | ./only_result.sh