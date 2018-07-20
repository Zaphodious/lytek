#!/usr/bin/env bash
grep -i -s -E '(Testing|FAIL|expected|actual|Ran|errors|::>)'
echo "\n"
echo "\n"