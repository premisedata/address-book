#!/bin/bash

echo "NOTE: Ignore all these warnings..."

# Create the database
echo "CREATE DATABASE IF NOT EXISTS address_book" | ./mysql

# Load the dummy data
./mysql < raw-data/raw-data.sql
./mysql < raw-data/mock_address_data.sql

# Munge the data a bit so we can work with it in our REST service
./mysql < data-prep.sql

# Manually create associations...
for i in {1..50}; do
    ./mysql < generate-pair.sql
done