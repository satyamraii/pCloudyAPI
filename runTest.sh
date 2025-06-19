#!/bin/bash

set -e  # Exit immediately if a command fails

echo "🚀 Removing allure-results directory..."
rm -rf allure-results || { echo "❌ Failed to remove allure-results"; exit 1; }

echo "🚀 Removing Screenshot directory..."
rm -rf screenshot || { echo "❌ Failed to remove screenshot directory"; exit 1; }

echo "🧹 Running Maven clean..."
mvn clean || { echo "❌ mvn clean failed"; exit 1; }

echo "🛠  Running Maven tests..."
if ! mvn test; then
    echo "⚠️ Tests failed, but generating Allure report..."
fi

echo "📊 Generating and serving Allure report..."
trap 'echo "✅ Allure report stopped by user"; exit 0' SIGINT  # Handle Ctrl+C gracefully
allure serve || echo "⚠️ Allure report stopped or failed."

echo "✅ All steps completed (even if tests failed)!"

