# UnblockNeteaseMusic 自动同步配置说明

## 📋 概述

本项目已配置自动同步 `UnblockNeteaseMusic/server` 仓库的更新到 `dolby_beta` 项目中。

## 🔄 同步机制

### 自动触发条件

1. **定时任务**: 每周日凌晨 00:00 自动检查一次更新
   ```yaml
   schedule:
     - cron: '0 0 * * 0'
   ```

   > 基于 UnblockNeteaseMusic/server 项目的实际更新频率（约每2个月一次），调整为每周同步一次。

2. **手动触发**: 通过 GitHub Actions 页面手动运行
   - 路径: Actions → Sync UnblockNeteaseMusic & Build → Run workflow

3. **代码推送**: 推送到 `master` 或 `develop/*` 分支时自动触发

4. **仓库事件**: 接收 `repository_dispatch` 事件触发
   - 类型: `sync-unm`

## 📦 同步内容

- **分支**: `enhanced` (UnblockNeteaseMusic/server 的主开发分支)
- **内容**: 完整的 Node.js 源码
- **目标**: `app/src/main/assets/UnblockNeteaseMusic.zip`

## 🔧 工作流详情

### `sync-unm.yml` 工作流程

1. **获取最新代码**: 从 `enhanced` 分支 clone 最新源码
2. **对比Commit**: 与本地存储的上次同步 commit 对比
3. **智能更新**:
   - 如果有更新 → 打包 → 构建APK → 提交PR/直接推送
   - 如果无更新 → 结束流程

4. **构建APK**: 使用 Gradle 构建 release 版本

5. **版本记录**: 在 `.unm_last_commit` 文件中记录当前同步的 commit hash

## 📊 最近同步记录

当前仓库已配置自动同步功能。

## 🔐 权限说明

工作流使用 `github-actions[bot]` 账号进行:
- 代码克隆
- 文件打包
- Gradle 构建
- Git 提交和推送

## ⚙️ 自定义配置

如需修改同步策略，可编辑 `.github/workflows/sync-unm.yml`:

### 修改同步频率
```yaml
schedule:
  - cron: '0 0 * * 0'  # 改为其他频率，如 '0 0 * * 1' (每周一)
```

### 修改目标分支
```yaml
env:
  UNM_BRANCH: enhanced  # 可改为 'master' 或其他分支
```

### 修改仓库
```yaml
env:
  UNM_REPO: UnblockNeteaseMusic/server
```

## 🚀 手动触发同步

1. 访问 GitHub 仓库: https://github.com/liem0352/dolby_beta
2. 点击 "Actions" 标签
3. 选择 "Sync UnblockNeteaseMusic & Build"
4. 点击 "Run workflow"
5. 选择分支并点击 "Run workflow"

## 📝 注意事项

- 工作流仅在 `liem0352/dolby_beta` 仓库运行
- 如果 UnblockNeteaseMusic 源码较大，首次同步可能需要较长时间
- 构建需要有效的签名配置 (已在项目中配置)
- APK 将作为 GitHub Actions artifact 保存 30 天

## ❓ 常见问题

**Q: 如何查看同步历史?**
A: 在 GitHub Actions 页面查看 "Sync UnblockNeteaseMusic & Build" 的运行历史

**Q: 如果同步失败怎么办?**
A: 检查 Actions 日志，根据错误信息修复后重新运行

**Q: 可以禁用自动同步吗?**
A: 可以通过删除或重命名 `.github/workflows/sync-unm.yml` 文件来禁用

## 📞 联系

如有问题，请在 GitHub 仓库提交 Issue
