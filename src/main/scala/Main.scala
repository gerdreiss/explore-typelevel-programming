// Numbers
trait Nat
class _0             extends Nat
class Succ[N <: Nat] extends Nat

type _1 = Succ[_0]
type _2 = Succ[_1]
type _3 = Succ[_2]
type _4 = Succ[_3]
type _5 = Succ[_4]

// Player
type X      = "X"
type O      = "O"
type Empty  = "_"
type Player = X | O
type Cell   = Player | Empty

// type level list
trait HList
class HNil              extends HList
class ::[H, T <: HList] extends HList

// board
type Board      = HList
type EmptyRow   = Empty :: Empty :: Empty :: HNil
type XRow       = X :: X :: X :: HNil
type ORow       = O :: O :: O :: HNil
type EmptyBoard = EmptyRow :: EmptyRow :: EmptyRow :: HNil

// satisfying predicates
trait Length[L <: HList, N <: Nat] // Length[L, N] exists if the length of L is N
given zeroLength: Length[HNil, _0] with                                                          {}
given lengthInductive[H, T <: HList, N <: Nat](using Length[T, N]): Length[H :: T, Succ[N]] with {}

// who wins in a game of tic-tac-toe?
// winner by row
trait SameValues[L <: HList, V]
given svBasic[V]: SameValues[HNil, V] with                                           {}
given svInductive[T <: HList, V](using SameValues[T, V]): SameValues[V :: T, V] with {}

trait WinnerByRow[B <: Board, W <: Player]
given winsOneRow[R <: HList, RT <: HList, P <: Player](using SameValues[R, P]): WinnerByRow[R :: RT, P] with   {}
given winsAnyRow[R <: HList, RT <: HList, P <: Player](using WinnerByRow[RT, P]): WinnerByRow[R :: RT, P] with {}

// winner by column
// take nth element
trait TakeNth[L <: HList, N <: Nat, V]
given tnBasic[T <: HList, V]: TakeNth[V :: T, _0, V] with                                               {}
given tnInductive[H, T <: HList, N <: Nat, V](using TakeNth[T, N, V]): TakeNth[H :: T, Succ[N], V] with {}

// map column
trait MapColumn[B <: Board, C <: Nat, O <: HList]
// continue here: https://youtu.be/sqTtZ3BQnRQ?t=1487

// winner by diagonal 1

// winner by diagonal 2

@main def hello: Unit =
  val a = summon[WinnerByRow[XRow :: EmptyRow :: EmptyRow :: HNil, X]]
