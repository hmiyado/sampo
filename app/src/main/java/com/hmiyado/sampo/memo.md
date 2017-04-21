# 散歩ログアプリ Sampo
## できること
### 基本機能
- 散歩ログ収集
    - 移動したコースをログ収集する(GPS情報をローカルに置いておく)
- ログの閲覧
    - 収集したGPS情報を真っ白なキャンバスに描いて，あるいた記録を残すことができる
    - リアルタイムに歩いた後をみることができる

### Future Works
- スポット登録
- ナワバリ
    - チーム作成
   
## 開発規約
### 環境，仕様ライブラリ
Kotlin,RxKotlin,Kodein

### コード規約
Android Studioのリンタに従う

### 枠組み
MVP+DDD & Clean Architecture

- domain ドメインロジックを書く場所
    - モデル定義
    - 状態定義
    - 例外
- libs
    - 外部ライブラリやピュアな Kotlin を便利にできるする処理をおく
- repository
    - View 以外の外部世界．データソースとのやりとりの方法を定義する
        - repository データを保存できる場所
        - sensor データを保存はできず，ただただ観測するだけの場所
- usecase
    - Android 世界をラップする interface がおいてある
        - view に何か入力する： UseHogeViewSink
        - View から何か出力を受け取る: UseHogeViewSource
    - repository や view の usecase の結合部分も置いてある
- presenter
    - usecase の UseHogeViewSource の具体的な実装
    - (T) -> R のような返り値のあるメソッドの集合
- controller
    - usecase の UseHogeViewSink の具体的な実装
    - (T) -> Unit のような返り値のないメソッドの集合
- service
    - android で定義される service に対応する
- view
    - Activity, Fragment, カスタムビューがおいてある
        - ロジックはだいたい presenter や controller に移譲する
        - 入出力を頑張ってきれいにわける

domain, repository の interface, usecase は pure　に書く(Android の実装からは独立)


#### クリーンアーキテクチャ
- 外の世界: ユーザー操作（view, touch イベント）
    - 層１：presenter(view からのイベント)/controller(viewへの出力)
    - 層２：UseCase
- 外の世界：外部記憶
    - 層１：repository
    - 層２：UseCase
- 外の世界：センサー
    - 層１：service
    - 層２：UseCase
- 外の世界：通信（サーバー）
    - 層１：service
    - 層２：UseCase
- 中の世界：状態
    - 状態：State
    - 状態の保持と更新: Store
- 中の世界：入出力結合
    - Interaction(入力ユースケース, 出力ユースケース)

層の中で完結することはその中で完結させることは絶対してはならない．
層をまたぐ処理は全部domain．
domainが分厚くなりそう&状態をどこでもってどう更新するかの仕組みがない．

-> domain層も，入力，出力，入出力結合の３つに分割する！

### テスト
モデル，リポジトリ，ドメイン，サービスはテストを書く

### UI設計
Activityはひとつ
Fragmentを切り替えてなんとかする

## TODO
テスト書く　-> ドメインだけ？

## 開発予定

1. [x] GPS情報の取得 -> Service から Observable で
1. [x] GPS情報の記録
    - 時間情報の取得
    - 記録
    - バックグラウンドでの取得
1. [x] 記録の閲覧(1 month)
1. [ ] 地点の影響範囲決定(1 month)
1. [ ] ポイントの登録(1 month)
1. [ ] 試用・リリース(1 month)

## DI ライブラリ
Kodeinを導入(https://salomonbrys.github.io/Kodein/)

## TODO
- [x] CompassViewが見えるようにする
- [] 拡大縮小の上限・下限を設定する
    - 単位操作あたたりの拡大縮小倍率の変化をいい感じにする
    - たぶん現状だと，拡大縮小倍率がすごく動きやすすぎる
- [] Orientation
    - [] Orientation に OriginalLocation から逆算した値を入れて，北を示すようにする
    - [] Compass をタップしたら，北固定モードと自身の向き固定モードを切り替えられるようにする
- [x] 地点のテスト追加
    - たぶんバグってる
    - 0, 境界値(経度はプラマイ180度，緯度はプラマイ90度，それぞれと超える場合)，例外値(経緯度が範囲外の場合)，経路の方向(8方向は入れたい)
- [x] 地点の経時変化をSubscribeする
    - [x] Viewに反映されるようにする
    - [x] Repositoryに反映されるようにする
        - [x] Realm に正しいスレッドからアクセスする
- [] mesh のscaleを定義する
    - scaleに従って，meshがいい感じに変化する(100m単位，1km単位などで切り替えるとよさそう？)
    - メッシュ単位の表示
- [] マテリアルデザインの勉強と変更
    - タッチ可能UIはマテリアルっぽくする
        - コンパスとか
- [] CI(継続的インテグレーション)導入
    - Firebase Test Lab for Android は無料？
    - https://firebase.googleblog.com/2017/01/BoostingAppQuality2017.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed:+FirebaseBlog+(The+Firebase+Blog)
- [x] Location のLat/LongをDegreeに変える
    - Degreeは十進度数法だと明記する(度分秒法もあるので)
- [x] バックグラウンドでLocationを取得する
    - https://developer.android.com/guide/components/services.html?hl=ja
    - フォアグラウンドサービス
    - 通知領域で距離確認，位置情報取得の可否を設定できるように試用
- [x] onResume で何度も subscribe される問題
- [x] 画面を回転するとぶっ壊れる問題
    - 回転するたびにフラグメントが再生成されているのが原因．
- [] 結果を表示できるActivity，Fragmentを作る
    - [x] Realm のリスト
    - [x] 距離計算結果
    - [x] エリア計算結果
- [x] 累計距離計算
- [x] エリア計算
    - エリアの計算方法をどうする？
        - 各点を中心とする円領域
        - [x] 地表面を分割した区画
- [] twitter 連携
- [] slack 連携
- [x] デザイン
    - [x] アイコンの変更　https://material.io/icons/
    - [x] テーマの設定
- [] 描画の際のスコア計算をバックグラウンドスレッドで行う / スコアを Territory でもって，メモ化しておく
- [] 通知欄をタップしたら MapActivity をたちあげる
- [] drawableFootmarks has not been initialized でアプリ落ちる問題

## 4月中の実装予定
配布可能な状態にする

- 新しいモデルの保存
    - [x] Marker
- マーカーの上限数規定
    - [x] 上限数を Store にもつ
    - [x] 上限数個になったら， Add Markers を disable にする
- [x] スコアを計算する
- スコアを閲覧可能にする
    - [x] map 画面
    - [x] result 画面
- 通知欄の改良
    - [x] アイコン
    - [x] テキスト(なわばり)
- ライセンス表示
    - Settings
        - [x] アプリについて
        - [x] ライセンス表示
- [x] パッケージ名の修正
- [] まともに見れるようにする
    - [] List Item
    - [] スコア画面
        - [] Daily Score
        - [] Weekly Score
        - [] Area Achievement
- [] 地図画面から位置情報観測サービスを開始できるようにする

## MapStore Singleton 問題
MapStore のシングルトンを Activity で抱えていることに寄って・・・

- Sensor サービスと　MapActivityが微妙に切り離せない
- MapStore が増大するとアプリケーション全部がやば

みたいな問題が起こっているっぽかったので，切り分ける．

- SensorService ではロケーションをリポジトリに保存して，Activityに通知を送る
- Activity では通知を受けて新しいロケーションをリポジトリから掘り出す

ということを行う．
SensorService から Activity には Intent を投げつける．
もう面倒だから，独自定義のActionでいいだろう．
Activity 側では，それを受ける Reciever を準備しておく．

## 3月の実装予定
- 4月までの実装: ゲーム性の向上
    - [] 結果画面の再構築(脱リストビュー)
    - [x] 距離計算
    - [x] エリア計算
        - [x] エリア計算の方法考案
        - [x] 実装
    - [] 数字の調整・調査
        - [] scale の正しい数字
        - [] score の調整
    - [] アチーブメントの設定
    - [] SNS 連携
    
    

### 結果画面の再構築
結果をぱっとみて

- 移動距離情報　さんぽ
    - 総距離
    - 直近（１週間？）の距離
    - 緻密に計算，ファジーに出力
- なわばり（範囲情報）
    - 1ヶ月
    - 1週間
    - 緻密に計算，ファジーに出力
- その他への導線
    - Achievement
    - Share
    - Repository(on develop)

参考資料
https://www.press.tokai.ac.jp/webtokai/kaiyuwatarikisou09.pdf

### 距離計算
Android の Location の accuracy はその範囲内に入っている確率が 68% であるような円の半径を表す．
のだが，余裕で accuracy  が 200 とかになってしまうことがわかった．

実際に走ってみたけど，履歴を「点」として表示するのはあんまり筋がよくない感じがした．
ちょっとなわばりを定義してみたい．

なわばり．
100m × 100m の区域にわける．
経度0，緯度0の地点から 100 × 100 にわけていく．
およそ51G個のエリアにわかれるはず．

sqrt(51G) = 225853.9351	→ 225855
経緯度をそれぞれ↑で等分した格子で区分される領域
180/225855 = 0.00079697151
360/225855 = 0.001593943

35.70388198 + 0.00079697151 = 35.704679
139.78259325 + 0.001593943 = 139.78419

自宅近辺で試してみると，直線距離で169.416になっているので，だいたい直径100〜150ｍの範囲に収まってそうなのでよさそう．

ある地点の経緯度から，その地点がどの領域に属するか，直接番号をとれるはず．

極座標で表される地点P(t, s)について
分割数をdとすると，
緯線の単位度数 lng_unit = 180/d
経線の単位度数 lat_unit = 360/d

P が属する領域は，floor(t + 90/ lng_unit) * d + floor(s / lat_unit)

地点(Location)はある領域(Area)に属する．
Area に Location が1つ増えると，Area の territory score が1増える．
また，Areaに隣接するAreaのterritory score は 1/x 増える(x: 影響力)．
以下，n回の隣接Areaのterritory scoreが増加する（？　連鎖影響の実装が面倒そうなのでやめといたほうがよさげ）．
territory score　に応じて，なわばりの色を変える

### スコアとレベルデザイン
ユーザーのリソース 1day = 24h = 24 * 60 min
少なくとも2分に1回地点を観測　24 * 60 / 2 = 720 locations/day = 7 * 720 locations/week

移動が多い狩猟型と移動が少ない農耕型

農耕型は1週間で1つのTerritoryに対して最大 7 * 720 地点
狩猟型は1週間で 7 * 720 のTerritoryに対して 1　地点． 7 * 720 * 10 = 50400

1週間で2100地点 = 1日で300地点 = 1時間でおよそ14地点 = 1地点あたり4分

#### スコア計算に係る要素
- マーカー
    - 影響範囲
    - 数
    - 倍率
    - 種類（拠点 base, マーカー marker）
- Territory ボーナス
- Territory 連続滞在期間
- Territory 滞在期間
- Territory 広さ

#### スコアの算出
total score = f(territories) * Sigma(territory score)
f: Territory の広さ(数)による倍率

territory score = (bonus + g(最大連続滞在期間) + h(滞在期間)) * marking

marking: マーカーによる倍率
bonus: Territory に1地点分でも滞在したら入る定数スコア
g: 最大連続滞在期間に依存するスコア．多項式オーダーがよい？
h: 滞在期間に依存するスコア．シグモイドのような，長期間滞在すると伸びが悪くなるスコア関数がよい？

- [] Territory 1日あたりのスコア実装
    - [x] location 依存部分
    - [x] 広さボーナスの実装
    - [] マーカー依存部分
        - [x] マーカーの作成
        - [] マーカーの影響力の決定
- [] Territory weekly score 実装

#### モデルケース
モデルケースを設定し，スコアを調整していくのがよいのではないか？

- 農耕特化：　自宅引きこもり．24時間自宅
- 農耕重点型：　2箇所を往復する．自宅12時間，移動2時間，仕事10時間
- 狩猟特化型：　日本一周中．同じ場所は2度と通らない．

## Area, Territory のユースケース
- 地点 → Area を特定
- 地点をAreaに登録
- Area の territory score が増える
- score に応じて

- Area
    - 分割数(static)
    - 

- 新しい Location がくる
- Location が属する Territory を特定する
- Territory に Location を追加する
- Territory のスコアが更新される

## 2月の実装予定
- 3月までの実装: 位置情報の記録
    - [x] production/develop でビルド環境を変える
        - 特に，Repository を変える
    - [x] Repository の確認をできるようにする
        - see 結果を表示できるActivity，Fragmentを作る

## 位置情報の設定
- [x] 位置情報の設定を取得できる
- [x] 位置情報取得設定がONなら，取得を行う
- [x] 位置情報取得設定がOFFなら，取得しない

### 位置情報のシステム設定を取得する
LOCATION_MODE API に LocationManager.MODE_CHANGED_ACTION が対応している．
BroadcastReciever で LocationManager.MODE_CHANGED_ACTION を使えば，システム設定の変更を取得できる

isProviderEnabled に LocationManager.PROVIDERS_CHANGED_ACTION が対応している．
provider 固有のメソッド(requestLocationUpdates など)を使っているなら， LOCATION_MODE ではなく，こちらを使うべき．
-> たしかに使っているので， PROVIDERS_CHANGED_ACTION を使うべきっぽい

http://stackoverflow.com/questions/20673620/how-to-trigger-broadcast-receiver-when-gps-is-turn-on-off

## ビルド環境を変える
ビルド環境が変わると，repository が変わるようにする → repository の名前を変更したので大丈夫なはず

ビルド環境を変えると，自動的に inject する実装が変わって欲しい

debug: デバッグモード．ログがでる
release: リリースモード．ログはでない

flavor

mock: モックされたデータが流れてくる
real: モックされてないデータが流れてくる

real のときに，ちゃんとmap が更新されない問題（Locationの時間がまったく変わらない．なぜ？）
なんか，どうもちゃんと位置情報を取得できていない感じがする．
要調査（mock ではうまくいく）

mock もあんまりうまくいってなかった（リポジトリの表示が）
うまくいくようになった．

そして，Debug 時にリポジトリを爆破できていないっぽいことがわかった

不具合

- LocalDateTime 正しく年月日に変換できない -> ライブラリ使っちゃいかんのか？
- unix time のまま保存しとけばよいのでは？ -> 位置情報を取得するたびに

## 保存する量の概算
1ヶ月間で30秒毎に1地点保存するとする

- 1分で2地点
- 60分で120地点
- 24時間で2880地点
- 30日で86400地点
- 1地点あたり数値3つ3バイト？
- 10バイトとしても864KB．知れてる．
- もう全部記録してもいいレベル．雑魚．

## Realm
### Rx バージョン管理
- Realm: ~1.1.x
- RxKotlin: 1.1.5
- RxAndroid:
    - 1.2.1 -> RXJava 1.1.6
    - 1.2.0 -> RxJava 1.1.4

RxKotlin を使っている以上，ここに依存しなければどうしようもなさそうなのであった．

### LocationRepository の実装不味すぎ問題
realm オブジェクトは外から注入する形にするか，その場で取得する形にしないと，他の場所から使えない．
少なくとも，realm の初期化は別の場所でやるべき

## 地点テストケース列挙
- [x] 経路の方向
    - (0,0)からどちらに行くか
    - 北 (1,0)
    - 北東 (1,1)
    - 東 (0,1)
    - 南東
    - 南 (-1,0)
    - 南西
    - 西 (0,-1)
    - 北西
    - 同一地点 (0,0)
- 境界値
    - 経度
        - [x] 1 -> 2(東経同士)
        - [x] -1 -> -2(西経同士)
        - [x] 1 -> -1(東経から西経)
        - [x] -1 -> 1(西経から東経)
        - [x] 180 -> -180(東経１８０度＝西経-180度は同一地点を指すはず)
    - 緯度
        - [x] 1 -> 2 (北緯同士)
        - [x] -1 -> -2 (南緯同士)
        - [x] 1 -> -1 (北緯から南緯)
        - [x] -1 -> 1 (南緯から北緯)
        - [x] 90 -> ? (北端を含むとき)
        - [x] -90 -> ? (南端を含むとき)
- 例外値
    - 経度
        - 180 より大きい
        - -180 より小さい
    - 緯度
        - 90 より大きい
        - -90 より小さい

## GNSS測地(GPS位置)情報からの距離・方位角の計算
２点間の描画のためには，距離と方位角が必要．
ちなみに，方位角とは，出発地を通る緯線から，出発地と到着地の最短経路への，右回りに図った角である．
求め方には，いくつか種類がある．

- 世界測地系
    - 現在，国土地理院が使用している測地系
    - http://www.gsi.go.jp/sokuchikijun/datum-main.html
    - http://vldb.gsi.go.jp/sokuchi/surveycalc/surveycalc/algorithm/bl2st/bl2st.htm
    - 実装が煩雑かつ多くの点に対して毎描画ごとに実行するにはおもすぎるのでは？　という気持ち
- 平面直角座標系
    - 国土地理院の定めによる
    - http://vldb.gsi.go.jp/sokuchi/surveycalc/surveycalc/algorithm/bl2xy/bl2xy.htm
    - 平面上に投影して計算する
    - 世界測地系より簡便にみえる
    - 130km四方を対象とするようだが，倍率とか工夫すれば，画面内を130kmに抑えることは十分可能だと思う（？）
        - 新幹線とかで旅行したときに問題が起こりそうだが・・・
        - そもそも，描画区域内に点がいっぱいあるときのことを考えてなかった
        - 描画するときの，最小描画単位みたいなのを決めたほうがよさそうか？
- [x] 球面三角法
    - 平面直角座標系よりさらに簡便
    - http://www.orsj.or.jp/archive2/or60-12/or60_12_701.pdf
    - http://www.astro.sci.yamaguchi-u.ac.jp/~kenta/eclipse/SphericalTriangle081106.pdf
    - 東京福岡間で誤差0.3%(2km)
    - まあ問題にならないレベルでは
    - 方位角も球面三角法における正弦定理で導出できそう
    - とりあえずこれを実装して，距離と方位角を計算できるようにしよう
- [x] 平面近似法
    - 勝手に考えた
    - 非常に近い点同士ならば，球面三角法を使うまでもなく，平面に近似して計算できるのでは？　という考え方
        - 問題設定
            - P1(lat1, lon1) から P2(lat2, lon2) への経路と方位角を求める
                - lat, lon はそれぞれRadianとする
            - 平均半径をRとする
        - 経路距離
            - 南北方向の経路 distNS = R * |lat1 - lat2|
            - 東西方向の経路 distEW = R * |lon1 - lon2|
            - 求める経路 dist = sqrt(distNS^2 + distEW^2)
        - 方位角
            - P1, P2, P1を通って経線に平行な直線にP2からおろした垂線の交わる点をHとして，三角形P1,P2,Hを考える
            - 角P1 = arctan( distNS / distEW )
            - P1 を通る経線をx軸(東方向を正)，緯線をy軸(北方向を正)としたときに，P2が第何象限にあるかで場合分けして，方位角thetaを求める
                - 第一象限：theta = PI / 2 - 角P1
                - 第二象限：theta = PI / 2 + 角P1
                - 第三象限：theta = 3 * PI / 2 - 角P1
                - 第四象限：theta = 3 * PI / 2 + 角P1
- [x] 中身の実装を変えられるように，interface をこさえておこう

## 記録の閲覧
位置情報をキャンバスに打点する
そろそろモックとか作ったほうがいいかもしれない？

地表に対する視点の位置を決める
1画面に収まるLocationの範囲を決める
視点から見えるLocationの範囲を計算する
点の大きさを決める

地図の縮尺を変更する（ピンチ操作）
地図の視点を回転する

とりあえず，視点は真上にする．
見えている範囲は，横を100mとする

GPS地点からm距離に変換するメソッドが必要
現在地点も必要

現在地点
DBからロードした地点
アプリ起動後に観測した地点 -> アプリ終了時にDBに保存する

ヒュベニの公式

Locationの距離の測り方は実装による．
ここは，UseLocationに抽象定義して，距離実装だけ別にわけるべきな気がしてきた
実装のひとつが，ヒュベニの公式？

ヒュべニの公式の実装はできた．
Mapを打点する用のViewも作った．(MapView)
あとは，どういう手順でViewに打点するか，だ
そのためには，

- [x] MapViewの固定の図形・文字を表示する
- [x] MapViewに対して位置情報を流して表示する
- [x] MapViewに対して図形(位置情報を示す○的な図形)を描画する
- [ ] 位置情報を打点する方法
    - [ ] Viewが捉える範囲を計算
    - [ ] View上に地点を打点

### 位置を打点する方法
位置を打点する方法の前に，縮尺，方位を導入すべきだと思う．
そうじゃないと，検証できるかどうかもわからない．
並行して，ピンチ操作，回転操作ができるようにしなければならない．

##### 方位磁針
方角の基準がないと，原点と任意の地点との角を図れない

方位磁針を作りたい

#### 縮尺の実装
ピンチ，回転操作の実装は3段階に別れている

- [x] ピンチ，回転の取得
    - [x] ピンチ比率の取得
    - [x] 回転角の取得
- [x] 拡大，縮小，回転処理の実装
    - [x] 拡大
    - [x] 縮小
    - [x] 回転
- [] 結合

それぞれ順番にやろう

100pxあたり何mかは定義できた．
あとは自身の地点（原点）と任意の地点との地点の相対関係をcanvas上の座標に変換出来ればよい

- 原点O（地点Lo(latiO, longO), 画面上の座標(0,0)）
- 点P（地点Lp(latiO, longO)）の座標(p1,p2)
- 100pxあたりの距離s
- 北向きの方向ベクトルN (n1, n2)

1. 地点LoとLpの距離dL(メートル)を求める
    - dL = HubenyFormula.determineDistance(Lo, Lp)
1. 画面上での距離 dD = s * dL (メートル)
1. 北向きの方向ベクトルN と 地点Loと地点Lpのなす角thetaを求める
1. 求める座標は( dD * cos(theta), dD * sin(theta) )

問題　北向きの方向ベクトルと，地点間のベクトルとのなす角

#### 方位磁針の実装
方位磁針はMapViewと別の独立したクラスとして実装したい．
そもそも，方角情報は，Repositoryから取得するのではないだろうか？

北向き/端末の向き両方取得しなければならない．

端末の向きは取得可能になった．
北向きは端末の向きから逆算して取得可能．

#### メッシュ
わかりやすくするのと，画面が映えるようにメッシュを入れたい

#### 画面に表示する情報
- [x] 現在地点(original location)
- [x] メッシュ(mesh)
- [x] 方位磁針
- 足跡

## GPS情報の記録
とりあえず，30秒ごとに記録する
記録した結果を，端末に登録する
記録を画面から消したり出したりできるようにする

LocationにDateTime突っ込むだけでクソめんどくさいことこの上なかった
data class は結局そのデータ特有のアクセッサとか定義しようとしたら崩壊するからダメ
継承できないからヌルオブジェクト定義できない

1月30日の1ヶ月後は何日？
というのを定義するために，abstract Month を継承したJanuary...December を作る
abstract Month {
    private val end_day
}

1月30日の1ヶ月後→日付が，末日を超えていたら，日付を末日に合わせる　

TimePointとTimeIntervalに概念を分ける？
Timepoint　時点：何年何月何日を表す．それ以上の概念はない
TimeInterval 時間：時間経過を表す

現状の問題点：LocalDateTime,Year,Monthの.toSecond()が表すものと，Day,Hour,Second,MinuteのtoSecond()が表すものが違う
Day, Hour, Minute, Second は，絶対的に,toSecondを定義できる
でも，LocalDateTime, Year, Monthは，相対的な定義（Monthは年初からの経過秒，LocalDateTime, Yearは西暦0年からの経過秒）になってしまっている
Year, Month はtoSecondを定義できない！！
相対を定義する

テスト上はtoUnixTimeなんかを実装できた．
だが，実アプリケーションでStackOverflow
1ヶ月先にすすめるごとに1回buildを呼ぶのはさすがにひどいかもしれない．
とりあえずwhileループあたりの実装に変えよう

有用ライブラリを導入する
http://qiita.com/EnoMt/items/0e0d4b1ba2693f625afc
優先順位
Timber.d　ログは大事　ビルド設定も同じく
Realm　DB使いやすく鳴るらしい．要件にあってる
Dagger2　いるかな？　DIがまだ感覚的につかめていない

## GPS情報の取得
UseLocationでできるようになった
テストを書こう
テストはdomainに対して書く，でいいか
ロジックなさすぎて書くことなかった

現状，domainに対するテストはLocationServiceに対するテスト
それはつまり，LocationServiceImplに対するテスト
それはさらに言えば，LocationManagerのテスト
ここをモックしたらもう別物のテストになる
