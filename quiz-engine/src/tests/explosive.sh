#!/bin/bash

for i in {00000..01000}
do
    echo ${i} > "../../${i}.text"
done
