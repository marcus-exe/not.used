#!/bin/bash
# Script to remove files from git tracking that should be ignored

echo "Removing files from git tracking that match .gitignore patterns..."
echo ""

# Remove build artifacts and other ignored files
git ls-files | grep -E "\.DS_Store$|local\.properties$|\.iml$|\.apk$|\.dex$|\.class$|\.log$|\.env$|\.env\.local$|build/|bin/|obj/|\.gradle/|\.idea/|\.vscode/|\.vs/" | xargs git rm --cached

echo ""
echo "Done! Files have been removed from git tracking but kept locally."
echo "Next steps:"
echo "  1. Review the changes: git status"
echo "  2. Commit the removal: git commit -m 'Remove build artifacts and ignored files from tracking'"

