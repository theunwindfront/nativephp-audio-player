---
description: Commit changes, bump version, and trigger a fresh release
---

This workflow automates the process of resetting the repository history (if requested) or simply committing, tagging, and pushing a new version.

### Prerequisites

- Ensure you have configured your git email and name (already done).

### Steps

1. **Reset History (Optional)**
   If you want to start with a fresh initial commit (recommended for the first time):
   ```bash
   rm -rf .git
   git init
   git remote add origin git@github.com:theunwindfront/nativephp-audio.git
   git checkout -b main
   ```

2. **Stage and Commit**
   ```bash
   git add .
   git commit -m "Initial commit"
   ```

3. **Bump Version (Standard Release)**
   For subsequent releases, use this step to bump version in `composer.json` and update `RELEASE_NOTES.md`.
   ```bash
   # Update composer.json version field
   # Update RELEASE_NOTES.md with new version header
   ```

4. **Tag and Push**
   ```bash
   git tag v1.1.1
   git push -u origin main --force
   git push origin v1.1.1 --force
   ```

### Automation Script (Next Release)

// turbo
To release the next version, use this command:
```bash
# This is a placeholder for the agent to help with next version
# Usage: /commit-release
```
