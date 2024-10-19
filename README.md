  # SkyJet

## 此專案將不再更新
**自己開服教學：**
1. [下載檔案](https://github.com/mailisme/SkyJet/releases/tag/v2.5.0)
2. 解壓縮
3. 安裝Java21
4. 進入MinecraftServer資料夾
5. 點start.bat(linux點start.sh)
6. [下載並安裝 ngrok (linux)](https://askie.today/ngrok-localhost-server-settings/)[ngrok (windows & mac)](https://israynotarray.com/other/20230210/1090666501/)
7. 打開cmd
8. 跑```ngrok tcp 25565```
9. 複製ip給朋友一起玩！！！

## 規則
**道具:**

註：道具皆為一次性

| 名稱   | 描述                                           |持續時間(作用時間)    |
|------|----------------------------------------------|---------------------|
| 劍魂之石 | 賦予增加攻擊力Ⅰ                                |10s|
| 冷陸氣團 | 投射時在撞擊點生成直徑7格的圓冰                   |2s|
| 虛影斗篷 | 賦予隱形效果                                   |5s|
| 地球之心 | 震走半徑6格內的實體                             |瞬間|
| 反射之盾 | 反彈所受傷害的50%                              |10s|
| 風行之靴 | 賦予加速效果Ⅵ                                 |10s|

**技能:**

註：每個人在進入遊戲前都可以選擇一個技能，技能就是一個在遊戲一開始就有的道具
註：技能是可以重複使用的，不過有冷卻時間

| 名稱   | 描述                    | 冷卻時間     |
|------|-----------------------|----------|
| 走為上策 | 隨血量降低，加強裝備保護，但減弱攻擊    | 無(永遠生效)  |
| 苦力怕 | 在對手8格外蹲下隱形，接近對手放開爆炸   | 60s |
| 瞬間治癒 | 恢復玩家所缺少血量的50%         | 5s |
| 閃電突襲 | 瞬移到對手旁         | 10s |
| 扒手  | 震出對手隨機的道具 | 10s |

**其他**：

剛開始是蒐集道具階段，攻擊會關閉，30秒後進入PVP階段，再30秒進入蒐集道具階段，依此類推。

## 如何貢獻PVP地圖

1. 裝 Java 21、Java JDK 21
2. `git clone https://github.com/mailisme/MinecraftPvpPlugin.git -b world` (如果之前就有 clone git 的 repo 最好先備份起來)
3. 進入 `MinecraftPvpPlugin/MinecraftServer` -> 執行 `start.bat`
4. 打開 Minecraft 1.8.x 版本，多人 -> 連上 `localhost` 伺服器
5. 進入大廳後，握著鑽石劍點右鍵，會有一個選單出現，請點上面唯一一個選項(應該是斧頭)，以傳送到PVP世界
6. 更改地圖
7. 當你告一個段落，將`MinecraftServer/PVP1/region`裡的所有檔案利用 Github UI 上傳至 `world` branch 的 `MinecraftServer/PVP1/region`，並取一個適當的commit名稱
9. 大功告成！
     
註：若有人更動了地圖並上傳至Github了，請在自己更動前先`git pull`同步地圖

## Bug(遊玩特色)
1. 由於要更改name tag時用到NMS的因素，玩家skin會被忽略，無法正常顯示 (暫無解方)
2. ~~玩家飢餓值reset~~
2. ~~player /l error~~
3. ~~multi pvp place bug(未確定)~~
4. ~~PVP還是會生怪~~
5. ~~remove item~~
6. 目前用劍在進PVP的時候如果有方塊在可及範圍內就會偵測不到 (not very important)
7. ~~鑽石劍耐用度恢復~~

