git restore --staged .

git add MinecraftServer/PVP1/region/* | true
git commit -m "update pvp world (half-automated)" | true
git push | true


git stash | true
git checkout kotlin
git pull

git checkout world -- MinecraftServer/PVP1/region/*

yes | cp -rf MinecraftServer/PVP1/* MinecraftServer/PVP0
yes | cp -rf MinecraftServer/PVP1/* MinecraftServer/PVP2

rm MinecraftServer/PVP0/uid.dat
rm MinecraftServer/PVP2/uid.dat

git add MinecraftServer

git commit -m "merge pvp world from world branch (half-automated)"
git push

git checkout world
git stash pop
