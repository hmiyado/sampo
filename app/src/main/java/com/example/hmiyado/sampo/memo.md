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
Kotlin,RxKotlin

### コード規約
Android Studioのリンタに従う

### 枠組み
MVP+DDD

View と Presenter は1対1対応
View を触れるのは対応するPresenterだけ
PresenterはView間とModelとのロジックを操作する
Presenterをまとめる，ManagerやOperator，Stateみたいなものを定義することもある

DDDっぽく，関心事に対していくつかのレイヤー分けをする

| レイヤ | 意味　| 依存レイヤ |
|:--:|:--|:-- |
| domain/model | データ構造や，そのデータ構造を作るためのファクトリを持つ|なし |
| repository | データの取得，保存を行う|モデル|
| domain/usecase | 純粋な関心事のロジックを記述する|モデル，サービス|
| presenter|ビューから入力を受け取り，ビジネスロジックを適用し，他のプレゼンターを通してビューに反映する|他全部|
| view |UI．UIへのイベント定義もここ（ロジックは当然プレゼンターから注入する）|プレゼンター|

内部，外部ライブラリは必ずインターフェースに実装を注入する形でラップして使う．

### テスト
モデル，リポジトリ，ドメイン，サービスはテストを書く

### UI設計
Activityはひとつ
Fragmentを切り替えてなんとかする

## TODO
テスト書く

## 開発予定

1. GPS情報の取得 -> Service から Observable で
1. GPS情報の記録
1. 記録の閲覧
1. 

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

## GPS情報の取得
UseLocationでできるようになった
テストを書こう
テストはdomainに対して書く，でいいか
ロジックなさすぎて書くことなかった

現状，domainに対するテストはLocationServiceに対するテスト
それはつまり，LocationServiceImplに対するテスト
それはさらに言えば，LocationManagerのテスト
ここをモックしたらもう別物のテストになる
