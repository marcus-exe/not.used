#!/bin/bash
# Script to check and remove files from git tracking that should be ignored

echo "Checking for files that should be removed from git tracking..."
echo ""

# Check for common files that should be ignored
echo "=== Files matching .gitignore patterns ==="
git ls-files | grep -E "\.DS_Store$|local\.properties$|\.iml$|\.apk$|\.dex$|\.class$|\.log$|\.env$|\.env\.local$" | while read file; do
    echo "  - $file"
done

# Check for build directories
echo ""
echo "=== Build directories ==="
git ls-files | grep -E "build/|bin/|obj/|\.gradle/" | while read file; do
    echo "  - $file"
done

# Check for IDE directories
echo ""
echo "=== IDE directories ==="
git ls-files | grep -E "\.idea/|\.vscode/|\.vs/" | while read file; do
    echo "  - $file"
done

echo ""
echo "=== Summary ==="
FILES_TO_REMOVE=$(git ls-files | grep -E "\.DS_Store$|local\.properties$|\.iml$|\.apk$|\.dex$|\.class$|\.log$|\.env$|\.env\.local$|build/|bin/|obj/|\.gradle/|\.idea/|\.vscode/|\.vs/")

if [ -z "$FILES_TO_REMOVE" ]; then
    echo "✓ No files need to be removed from tracking!"
else
    echo "Files that should be removed:"
    echo "$FILES_TO_REMOVE" | while read file; do
        echo "  - $file"
    done
    echo ""
    echo "To remove these files from tracking (but keep them locally), run:"
    echo "  git rm --cached <file>"
    echo ""
    echo "Or remove all at once:"
    echo "  git ls-files | grep -E '\.DS_Store$|local\.properties$|\.iml$|\.apk$|\.dex$|\.class$|\.log$|\.env$|\.env\.local$|build/|bin/|obj/|\.gradle/|\.idea/|\.vscode/|\.vs/' | xargs git rm --cached"
fi

